package io.chipmango.revenuecat.viewmodel

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.revenuecat.purchases.CustomerInfo
import io.chipmango.revenuecat.RevenueCat
import io.chipmango.revenuecat.RcState
import io.chipmango.revenuecat.PurchaseListener
import com.revenuecat.purchases.Package
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RcViewModel @Inject constructor(
    private val revenueCat: RevenueCat
) : ViewModel() {

    val rcState: MutableStateFlow<RcState> = MutableStateFlow(RcState.Loading)

    fun fetchAvailableProducts() {
        viewModelScope.launch {
            revenueCat.fetchAvailableProducts(
                onError = { message ->
                    rcState.update { RcState.Error(message) }
                },
                onSuccess = { offers ->
                    rcState.update { RcState.Success(offers) }
                }
            )
        }
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

    fun savePurchase(customerInfo: CustomerInfo) {
        revenueCat.savePurchase(customerInfo)
    }
}