package io.chipmango.revenuecat.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.hilt.navigation.compose.hiltViewModel
import io.chipmango.revenuecat.viewmodel.PaywallViewModel

@Composable
fun isPremiumUser(): State<Boolean> {
    val purchaseViewModel = hiltViewModel<PaywallViewModel>()
    return produceState(initialValue = false) {
        purchaseViewModel.isPremium().collect { value = it }
    }
}