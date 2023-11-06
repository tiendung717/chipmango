package io.chipmango.iap

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.hilt.navigation.compose.hiltViewModel
import io.chipmango.iap.viewmodel.PurchaseViewModel

@Composable
fun isPremiumUser(): State<Boolean> {
    val purchaseViewModel = hiltViewModel<PurchaseViewModel>()
    return produceState(initialValue = false) {
        purchaseViewModel.isPremium().collect { value = it }
    }
}