package io.chipmango.ad.interstitial

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun PreLoadInterstitialAds(vararg adUnitId: String) {
    val viewModel = hiltViewModel<InterstitialAdViewModel>()

    LaunchedEffect(Unit) {
        viewModel.fetchAds(*adUnitId)
    }
}

