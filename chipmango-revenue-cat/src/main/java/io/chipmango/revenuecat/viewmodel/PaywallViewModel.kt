package io.chipmango.revenuecat.viewmodel

import android.app.Activity
import androidx.lifecycle.ViewModel
import io.chipmango.revenuecat.PaywallOffer
import io.chipmango.revenuecat.RevenueCat
import io.chipmango.revenuecat.PaywallState
import io.chipmango.revenuecat.PurchaseListener
import com.revenuecat.purchases.Package
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PaywallViewModel @Inject constructor(
    private val revenueCat: RevenueCat
) : ViewModel() {

    val paywallState: MutableStateFlow<PaywallState> = MutableStateFlow(PaywallState.Loading)

    fun fetchAvailableProducts() {
        revenueCat.fetchAvailableProducts(
            onError = { message ->
                paywallState.update { PaywallState.Error(message) }
            },
            onSuccess = { offers ->
                paywallState.update { PaywallState.Success(offers) }
            }
        )
    }

    fun makePurchase(
        activity: Activity,
        product: Package,
        purchaseListener: PurchaseListener? = null
    ) {
        revenueCat.makePurchase(activity, product, purchaseListener)
    }

    fun restorePurchase() {
        revenueCat.restorePurchase()
    }

    fun isPremium(): Flow<Boolean> {
        return revenueCat.isPremium()
    }
}