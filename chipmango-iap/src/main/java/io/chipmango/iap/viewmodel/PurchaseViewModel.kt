package io.chipmango.iap.viewmodel

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.billingclient.api.ProductDetails
import io.chipmango.iap.ChipmangoIap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PurchaseViewModel @Inject constructor(private val chipmangoIap: ChipmangoIap) : ViewModel() {

    fun restorePurchase() {
        chipmangoIap.restorePurchase()
    }

    fun buy(activity: Activity, productId: String, offerToken: String? = null): Boolean {
        return chipmangoIap.launchBillingFlow(activity, productId, offerToken)
    }

    fun isPremium(): Flow<Boolean> {
        return chipmangoIap.isPremium()
    }

    fun isPurchased(productId: String) : Flow<Boolean> {
        return chipmangoIap.isPurchased(productId)
    }

    fun unsubscribe(activity: Activity, sku: String) {
        chipmangoIap.unsubscribe(activity, sku)
    }

    fun getAllProducts(): Flow<List<ProductDetails>> {
        return chipmangoIap.getAllProducts().map { it.values.toList() }
    }

    fun getProduct(productId: String): Flow<ProductDetails?> {
        return chipmangoIap.getProduct(productId)
    }

    fun getMostPopularProductId() = chipmangoIap.getMostPopularProductId()

    fun listenPurchaseResult(productId: String, callback: () -> Unit) {
        viewModelScope.launch {
            isPurchased(productId).collect {
                if (it) callback()
            }
        }
    }
}