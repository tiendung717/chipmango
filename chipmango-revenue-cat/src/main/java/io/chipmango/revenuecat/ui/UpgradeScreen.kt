package io.chipmango.revenuecat.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import io.chipmango.revenuecat.RcOffer
import io.chipmango.revenuecat.RcState
import io.chipmango.revenuecat.viewmodel.RcViewModel

@Composable
fun UpgradeScreen(
    rcViewModel: RcViewModel = hiltViewModel(),
    contentLoading: @Composable () -> Unit,
    contentError: @Composable (String) -> Unit,
    content: @Composable (List<RcOffer>) -> Unit
) {
    val paywallState by rcViewModel.rcState.collectAsState()

    LaunchedEffect(Unit) {
        rcViewModel.fetchAvailableProducts()
    }

    when (paywallState) {
        is RcState.Loading -> {
            contentLoading()
        }

        is RcState.Success -> {
            val offers = remember { (paywallState as RcState.Success).offers }

            content(offers)
        }

        is RcState.Error -> {
            val errorMessage = remember {
                (paywallState as RcState.Error).message
            }
            contentError(errorMessage)
        }
    }
}