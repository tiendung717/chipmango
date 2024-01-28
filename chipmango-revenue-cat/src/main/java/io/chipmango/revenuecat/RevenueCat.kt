package io.chipmango.revenuecat

import android.app.Activity
import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.revenuecat.purchases.CustomerInfo
import io.chipmango.revenuecat.extensions.hasActiveEntitlements
import com.revenuecat.purchases.LogLevel
import com.revenuecat.purchases.Package
import com.revenuecat.purchases.PurchaseParams
import com.revenuecat.purchases.Purchases
import com.revenuecat.purchases.PurchasesConfiguration
import com.revenuecat.purchases.getOfferingsWith
import com.revenuecat.purchases.purchaseWith
import com.revenuecat.purchases.restorePurchasesWith
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RevenueCat @Inject constructor(@ApplicationContext private val context: Context) {

    companion object {
        private const val KEY_PREMIUM = "key_premium"
    }

    private val Context.dataStore by preferencesDataStore(name = "purchase.ds")
    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    fun init(context: Context, sdkKey: String, logEnabled: Boolean) {
        if (logEnabled) {
            Purchases.logLevel = LogLevel.DEBUG
        }
        Purchases.configure(PurchasesConfiguration.Builder(context, sdkKey).build())
    }

    fun fetchAvailableProducts(
        onError: (String) -> Unit,
        onSuccess: (List<RcOffer>) -> Unit
    ) {
        Purchases.sharedInstance.getOfferingsWith(
            onError = { error ->
                onError(error.message)
            },
            onSuccess = { offerings ->

                val offers = offerings.all.map { entry ->
                    RcOffer(
                        entry.value,
                        entry.value.getMetadataString("title", ""),
                        entry.value.getMetadataString("subtitle", ""),
                    )
                }
                onSuccess(offers)
            }
        )
    }

    fun makePurchase(
        activity: Activity,
        packageToPurchase: Package,
        purchaseListener: PurchaseListener? = null
    ) {
        Purchases.sharedInstance.purchaseWith(
            PurchaseParams.Builder(activity, packageToPurchase).build(),
            onError = { error, userCancelled ->
                if (userCancelled) {
                    purchaseListener?.onPurchaseCancelled()
                } else {
                    purchaseListener?.onPurchaseError(error)
                }
            },
            onSuccess = { _, customerInfo ->
                val isPremium = customerInfo.entitlements.hasActiveEntitlements()
                coroutineScope.launch {
                    save(KEY_PREMIUM, isPremium)
                }.invokeOnCompletion {
                    purchaseListener?.onPurchaseCompleted(customerInfo)
                }
            }
        )
    }

    fun restorePurchase(purchaseListener: PurchaseListener? = null) {
        Purchases.sharedInstance.restorePurchasesWith(
            onError = { error ->
                purchaseListener?.onPurchaseError(error)
            },
            onSuccess = { customerInfo ->
                val isPremium = customerInfo.entitlements.hasActiveEntitlements()
                coroutineScope.launch {
                    save(KEY_PREMIUM, isPremium)
                }.invokeOnCompletion {
                    purchaseListener?.onPurchaseCompleted(customerInfo)
                }
            }
        )
    }

    fun isPremium() : Flow<Boolean> {
        return read(KEY_PREMIUM, false)
    }

    private suspend fun save(key: String, value: Boolean) {
        context.dataStore.edit { settings ->
            val prefKey = booleanPreferencesKey(key)
            settings[prefKey] = value
        }
    }

    private fun read(key: String, defaultValue: Boolean): Flow<Boolean> {
        val prefKey = booleanPreferencesKey(key)
        return context.dataStore.data.map { it[prefKey] ?: defaultValue }
    }

    fun savePurchase(customerInfo: CustomerInfo) {
        val isPremium = customerInfo.entitlements.hasActiveEntitlements()
        coroutineScope.launch {
            save(KEY_PREMIUM, isPremium)
        }
    }
}