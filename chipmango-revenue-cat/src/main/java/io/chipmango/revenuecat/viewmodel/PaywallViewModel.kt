package io.chipmango.revenuecat.viewmodel

import android.app.Activity
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.revenuecat.purchases.CustomerInfo
import com.revenuecat.purchases.Offering
import io.chipmango.revenuecat.RevenueCat
import io.chipmango.revenuecat.domain.OfferingResult
import io.chipmango.revenuecat.PurchaseListener
import com.revenuecat.purchases.Package
import com.revenuecat.purchases.models.StoreProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import io.chipmango.revenuecat.domain.ProductsResult
import io.chipmango.revenuecat.receiver.DiscountReceiver
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.ZonedDateTime
import javax.inject.Inject

@HiltViewModel
class PaywallViewModel @Inject constructor(
    private val revenueCat: RevenueCat
) : ViewModel() {

    fun isPaywallOnboardingShown(): Boolean {
        return revenueCat.isPaywallOnboardingShown()
    }

    fun setPaywallOnboardingShown() {
        revenueCat.setPaywallOnboardingShown()
    }

    fun getCachedOffering() = revenueCat.getCachedOffering()

    fun getCachedProducts() = revenueCat.getCachedProducts()

    fun setCachedOffering(offering: Offering) {
        revenueCat.setCachedOffering(offering)
    }

    fun setCachedProducts(products: List<StoreProduct>) {
        revenueCat.setCachedProducts(products)
    }

    fun verifyInitialAppLaunch(onSubsequentLaunch: () -> Unit) {
        viewModelScope.launch {
            revenueCat.verifyInitialAppLaunch {
                onSubsequentLaunch()
            }
        }
    }

    fun loadCurrentOffering(
        onSuccess: (Offering) -> Unit,
        onError: (String) -> Unit
    ) {
        revenueCat.fetchCurrentOffer(
            onError = onError,
            onSuccess = { offer ->
                if (offer != null) {
                    onSuccess(offer)
                } else {
                    onError("No offering available")
                }
            }
        )
    }

    fun loadProducts(
        productIds: List<String>,
        onSuccess: (List<StoreProduct>) -> Unit,
        onError: (String) -> Unit
    ) {
        revenueCat.fetchProducts(
            productIds = productIds,
            onError = onError,
            onGetStoreProducts = { products ->
                if (products.isNotEmpty()) {
                    onSuccess(products)
                } else {
                    onError("No products available")
                }
            }
        )
    }

    fun loadOfferingAndDiscount(
        discountProductIds: List<String>,
        onError: (String) -> Unit,
        onSuccess: (Offering, List<StoreProduct>) -> Unit
    ) {
        viewModelScope.launch {
            loadCurrentOffering(
                onError = onError,
                onSuccess = { offering ->
                    revenueCat.setCachedOffering(offering)
                    loadProducts(
                        productIds = discountProductIds,
                        onError = onError,
                        onSuccess = { products ->
                            revenueCat.setCachedProducts(products)
                            onSuccess(offering, products)
                        }
                    )
                }
            )
        }
    }

    fun cancelSubscription(
        openManagementUri: (Uri) -> Unit,
        onNoActiveSubscription: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            revenueCat.cancelSubscription(
                openManagementUri,
                onNoActiveSubscription,
                onError
            )
        }
    }

    fun makePurchase(
        activity: Activity,
        product: Package,
        purchaseListener: PurchaseListener? = null
    ) {
        viewModelScope.launch {
            revenueCat.makePurchase(activity, product, purchaseListener)
        }
    }

    fun makePurchase(
        activity: Activity,
        product: StoreProduct,
        purchaseListener: PurchaseListener?
    ) {
        viewModelScope.launch {
            revenueCat.makePurchase(activity, product, purchaseListener)
        }
    }

    fun restorePurchase() {
        revenueCat.restorePurchase()
    }

    fun getRemainingDiscountDuration(): Duration {
        return revenueCat.getRemainingDiscountDuration()
    }

    fun isDiscountExpired() = revenueCat.isDiscountExpired()

    fun evaluateDiscountOfferDisplay(
        discountStartTime: ZonedDateTime,
        discountUniqueRequestId: Int,
        discountTitle: String,
        discountMessage: String,
        discountDuration: Duration,
        discountReceiverClass: Class<out DiscountReceiver>,
        shouldTriggerDiscount: (CustomerInfo) -> Boolean = {
            revenueCat.hasUserCancelledTrial(it) || revenueCat.hasUsedAppForDuration(it, Duration.ofDays(3))
        }
    ) {
        revenueCat.evaluateDiscountOfferDisplay(
            discountStartTime,
            discountUniqueRequestId,
            discountTitle,
            discountMessage,
            discountDuration,
            discountReceiverClass,
            shouldTriggerDiscount
        )
    }

    fun userCancelledTrial(customerInfo: CustomerInfo): Boolean {
        return revenueCat.hasUserCancelledTrial(customerInfo)
    }

    fun hasUsedAppForDuration(customerInfo: CustomerInfo, duration: Duration): Boolean {
        return revenueCat.hasUsedAppForDuration(customerInfo, duration)
    }

    fun getPremiumStatusFlow(): Flow<Boolean> {
        return revenueCat.getPremiumStatusFlow()
    }
}