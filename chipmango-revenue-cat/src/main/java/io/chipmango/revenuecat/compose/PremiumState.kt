package io.chipmango.revenuecat.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.hilt.navigation.compose.hiltViewModel
import io.chipmango.revenuecat.viewmodel.PaywallViewModel

@Composable
fun isPremiumUser(initialValue: Boolean = true): State<Boolean> {
    val paywallViewModel = hiltViewModel<PaywallViewModel>()
    return produceState(initialValue = initialValue) {
        paywallViewModel.getPremiumStatusFlow().collect { value = it }
    }
}