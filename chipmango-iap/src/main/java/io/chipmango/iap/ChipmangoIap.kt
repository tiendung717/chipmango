package io.chipmango.iap

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.android.billingclient.api.*
import com.android.billingclient.api.BillingClient.ProductType
import io.chipmango.iap.model.IapItem
import io.chipmango.iap.ext.isConsumable
import io.chipmango.iap.ext.isPending
import io.chipmango.iap.ext.isPurchased
import io.chipmango.iap.ext.isSuccess
import io.chipmango.iap.ext.isUserCancelled
import io.chipmango.iap.ext.verifySignature
import dagger.hilt.android.qualifiers.ApplicationContext
import io.chipmango.iap.di.ChipmangoIapConfiguration
import io.chipmango.iap.model.PremiumFeature
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChipmangoIap @Inject constructor(
    @ApplicationContext private val appContext: Context,
    @ChipmangoIapConfiguration private val configuration: IapConfiguration
) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private lateinit var billingClient: BillingClient
    private val productDetailsMap = MutableStateFlow<Map<String, ProductDetails>>(emptyMap())

    private val purchasesUpdatedListener = PurchasesUpdatedListener { billingResult, purchases ->
        if (purchases.isNullOrEmpty()) return@PurchasesUpdatedListener
        if (billingResult.isSuccess()) {
            purchases.onEach { handlePurchase(it) }
        } else if (billingResult.isUserCancelled()) {
            purchases.onEach { cancelPurchase(it) }
        }
    }

    private fun cancelPurchase(purchase: Purchase) {
        val productMap = productDetailsMap.value
        val productDetails = productMap[purchase.products.first()] ?: return
        savePurchaseStatus(productDetails.productId, false)
    }

    private fun savePurchaseStatus(productId: String, purchased: Boolean) {
        configuration.savePurchase(productId, purchased)
    }

    private fun saveOrderId(productId: String, orderId: String?) {
        configuration.saveOrderId(productId, orderId)
    }

    private fun savePurchaseToken(productId: String, token: String) {
        configuration.savePurchaseToken(productId, token)
    }

    private fun connectBillingService() {
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                coroutineScope.launch {
                    queryPurchaseDetails()
                    fetchPurchases()
                }
            }

            override fun onBillingServiceDisconnected() {
                connectBillingService()
            }
        })
    }

    private fun queryPurchaseDetails() {
        val productMap = configuration.availableIapItems().groupBy { it.type }

        val deferredProductDetails = productMap.map {
            val productList = it.value.map { purchaseItem ->
                QueryProductDetailsParams.Product.newBuilder()
                    .setProductId(purchaseItem.id)
                    .setProductType(purchaseItem.type)
                    .build()
            }
            val params = QueryProductDetailsParams.newBuilder()
            params.setProductList(productList)

            coroutineScope.async {
                billingClient.queryProductDetails(params.build())
            }
        }

        coroutineScope.launch {
            productDetailsMap.value = deferredProductDetails.awaitAll()
                .filter { it.billingResult.isSuccess() }
                .mapNotNull { it.productDetailsList }
                .flatten()
                .associateBy { it.productId }
        }
    }

    private fun handlePurchase(purchase: Purchase) {
        when {
            purchase.isPurchased() -> processPurchase(purchase)
            purchase.isPending() -> processPendingPurchase(purchase)
        }
    }

    private fun processPurchase(purchase: Purchase) {
        val productMap = productDetailsMap.value
        val productDetails = productMap[purchase.products.first()] ?: return
        // 1. Verify
        if (purchase.isPurchased() && configuration.iapLicense().verifySignature(purchase)) {

            // 2. Grant entitlement to the user.
            if (purchase.products.isNotEmpty()) {
                savePurchaseStatus(productDetails.productId, true)
                saveOrderId(productDetails.productId, purchase.orderId)
                savePurchaseToken(productDetails.productId, purchase.purchaseToken)
            }

            // 3. Consume or Ack
            when {
                purchase.isConsumable() -> {
                    val consumeParams = ConsumeParams.newBuilder()
                        .setPurchaseToken(purchase.purchaseToken)
                        .build()
                    coroutineScope.launch {
                        withContext(Dispatchers.IO) {
                            billingClient.consumePurchase(consumeParams)
                        }
                    }
                }
                !purchase.isAcknowledged -> {
                    val ackParams = AcknowledgePurchaseParams
                        .newBuilder()
                        .setPurchaseToken(purchase.purchaseToken)

                    coroutineScope.launch {
                        withContext(Dispatchers.IO) {
                            billingClient.acknowledgePurchase(ackParams.build())
                        }
                    }
                }
            }
        } else {
            coroutineScope.launch {
                savePurchaseStatus(productDetails.productId, false)
            }
        }
    }

    private fun processPendingPurchase(purchase: Purchase) {

    }

    private fun fetchPurchases() {
        val inAppParams = QueryPurchasesParams.newBuilder()
            .setProductType(ProductType.INAPP)
            .build()

        val subParams = QueryPurchasesParams.newBuilder()
            .setProductType(ProductType.SUBS)
            .build()

        coroutineScope.launch {
            val inAppResult = withContext(Dispatchers.IO) {
                billingClient.queryPurchasesAsync(params = inAppParams)
            }

            val subscriptionResult = withContext(Dispatchers.IO) {
                billingClient.queryPurchasesAsync(params = subParams)
            }

            listOf(inAppResult, subscriptionResult)
                .filter { it.billingResult.isSuccess() }
                .map { it.purchasesList }
                .flatten()
                .onEach {
                    handlePurchase(it)
                }
        }
    }

    fun init() {
        billingClient = BillingClient.newBuilder(appContext)
            .setListener(purchasesUpdatedListener)
            .enablePendingPurchases()
            .build()

        connectBillingService()
    }

    internal fun getAllProducts() = productDetailsMap

    internal fun getProduct(productId: String) = productDetailsMap.map { it[productId] }

    internal fun launchBillingFlow(activity: Activity, productId: String, offerToken: String? = null): Boolean {
        val productMap = productDetailsMap.value
        val productDetails = productMap[productId] ?: return false
        val productDetailsParamsList = listOf(
            BillingFlowParams.ProductDetailsParams.newBuilder()
                .setProductDetails(productDetails)
                .apply { offerToken?.let { setOfferToken(it) } }
                .build()
        )

        val billingFlowParams = BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(productDetailsParamsList)
            .build()

        val billingResult = billingClient.launchBillingFlow(activity, billingFlowParams)
        return billingResult.isSuccess()
    }

    internal fun getMostPopularProductId() = configuration.getMostPopularProductId()

    fun restorePurchase() {
        fetchPurchases()
    }

    fun isPurchased(productId: String): Flow<Boolean> {
        return configuration.isPurchased(productId)
    }

    fun isPremium(): Flow<Boolean> {
        return configuration.isPremium()
    }

    fun unsubscribe(activity: Activity, sku: String) {
        try {
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            val subscriptionUrl = ("http://play.google.com/store/account/subscriptions"
                    + "?package=" + activity.packageName
                    + "&sku=" + sku)
            intent.data = Uri.parse(subscriptionUrl)
            activity.startActivity(intent)
            activity.finish()
        } catch (e: Exception) {
            Timber.tag("nt.dung").e(e)
        }
    }

    fun getPremiumFeatureList(): List<PremiumFeature> {
        return configuration.getPremiumFeatureList()
    }
}