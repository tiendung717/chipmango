package io.chipmango.revenuecat.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import io.chipmango.revenuecat.PaywallOffer
import io.chipmango.revenuecat.PaywallState
import io.chipmango.revenuecat.viewmodel.PaywallViewModel

@Composable
fun UpgradeScreen(
    paywallViewModel: PaywallViewModel = hiltViewModel(),
    contentLoading: @Composable () -> Unit,
    contentError: @Composable (String) -> Unit,
    content: @Composable (List<PaywallOffer>) -> Unit
) {
    val paywallState by paywallViewModel.paywallState.collectAsState()

    LaunchedEffect(Unit) {
        paywallViewModel.fetchAvailableProducts()
    }

    when (paywallState) {
        is PaywallState.Loading -> {
            contentLoading()
        }

        is PaywallState.Success -> {
            val offers = remember { (paywallState as PaywallState.Success).offers }

            content(offers)
        }

        is PaywallState.Error -> {
            val errorMessage = remember {
                (paywallState as PaywallState.Error).message
            }
            contentError(errorMessage)
        }
    }
}