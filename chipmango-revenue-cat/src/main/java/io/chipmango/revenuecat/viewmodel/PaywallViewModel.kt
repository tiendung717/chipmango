package io.chipmango.revenuecat.viewmodel

import android.app.Activity
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.revenuecat.purchases.CustomerInfo
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

    private val _offeringResult: MutableStateFlow<OfferingResult> = MutableStateFlow(OfferingResult.Loading)
    val offeringResult: Flow<OfferingResult> = _offeringResult

    private val _productResult = MutableStateFlow<ProductsResult>(ProductsResult.Loading)
    val productResult: Flow<ProductsResult> = _productResult

    fun isPaywallOnboardingShown(): Boolean {
        return revenueCat.isPaywallOnboardingShown()
    }

    fun setPaywallOnboardingShown() {
        revenueCat.setPaywallOnboardingShown()
    }

    fun fetchCurrentOffering() {
        viewModelScope.launch {
            _offeringResult.value = OfferingResult.Loading
            revenueCat.fetchCurrentOffer(
                onError = { message ->
                    _offeringResult.value = OfferingResult.Error(message)
                },
                onSuccess = { offer ->
                    if (offer != null) {
                        _offeringResult.value = OfferingResult.Success(offer)
                    } else {
                        _offeringResult.value = OfferingResult.Error("No offering available")
                    }
                }
            )
        }
    }

    fun fetchProducts(productIds: List<String>) {
        viewModelScope.launch {
            revenueCat.fetchProducts(
                productIds = productIds,
                onError = { message ->
                    _productResult.value = ProductsResult.Error(message)
                },
                onGetStoreProducts = { products ->
                    if (products.isNotEmpty()) {
                        _productResult.value = ProductsResult.Success(products)
                    } else {
                        _productResult.value = ProductsResult.Error("No products available")
                    }
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