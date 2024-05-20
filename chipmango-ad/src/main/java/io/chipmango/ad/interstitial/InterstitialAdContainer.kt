package io.chipmango.ad.interstitial

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun InterstitalAdContainer(
    activity: Activity,
    adUnitId: String,
    shouldDisplayAd: () -> Boolean,
    onAdClosed: () -> Unit,
    onAdClicked: () -> Unit,
    onAdFailedToShow: () -> Unit,
    onAdNotAvailable: () -> Unit,
    onAdDisplayNotRequired: () -> Unit,
    content: @Composable () -> Unit
) {
    val viewModel = hiltViewModel<InterstitialAdViewModel>()
    LaunchedEffect(Unit) {
        if (shouldDisplayAd()) {
            viewModel.showInterstitialAd(
                activity = activity,
                adUnitId = adUnitId,
                onAdClosed = onAdClosed,
                onAdFailedToShow = onAdFailedToShow,
                onAdNotAvailable = onAdNotAvailable,
                onAdClicked = onAdClicked
            )
        } else {
            onAdDisplayNotRequired()
        }
    }
    content()
}