package io.chipmango.revenuecat.viewmodel

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    fun fetchCurrentOffer() {
        viewModelScope.launch {
            revenueCat.fetchCurrentOffer(
                onError = { message ->
                    rcState.update { RcState.Error(message) }
                },
                onSuccess = { offer ->
                    rcState.update { RcState.Success(offer) }
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
}