package io.chipmango.revenuecat.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.revenuecat.purchases.Offering
import io.chipmango.revenuecat.OfferingResult
import io.chipmango.revenuecat.viewmodel.PaywallViewModel

@Composable
fun OfferContainer(
    paywallViewModel: PaywallViewModel = hiltViewModel(),
    contentLoading: @Composable () -> Unit,
    contentError: @Composable (String) -> Unit,
    content: @Composable (Offering) -> Unit
) {
    val offeringResult by paywallViewModel.offeringResult.collectAsStateWithLifecycle(OfferingResult.Loading)

    LaunchedEffect(Unit) {
        paywallViewModel.fetchCurrentOffering()
    }

    when (offeringResult) {
        is OfferingResult.Loading -> {
            contentLoading()
        }

        is OfferingResult.Success -> {
            val offer = remember { (offeringResult as OfferingResult.Success).offer }
            content(offer)
        }

        is OfferingResult.Error -> {
            val errorMessage = remember {
                (offeringResult as OfferingResult.Error).message
            }
            contentError(errorMessage)
        }
    }
}