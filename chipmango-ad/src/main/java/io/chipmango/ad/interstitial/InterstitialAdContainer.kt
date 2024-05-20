package io.chipmango.ad.interstitial

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
private fun InterstitalAdContainer(
    activity: Activity,
    adUnitId: String,
    shouldDisplayAd: () -> Boolean,
    onAdClosed: () -> Unit,
    onAdClicked: () -> Unit,
    onAdFailedToShow: () -> Unit,
    onAdNotAvailable: () -> Unit,
    loadingContent: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    var adClosed by remember {
        mutableStateOf(false)
    }

    val viewModel = hiltViewModel<InterstitialAdViewModel>()

    if (adClosed.not() && shouldDisplayAd()) {
        LaunchedEffect(Unit) {
            viewModel.showInterstitialAd(
                activity = activity,
                adUnitId = adUnitId,
                onAdClosed = {
                    adClosed = true
                    onAdClosed()
                },
                onAdFailedToShow = {
                    adClosed = true
                    onAdFailedToShow()
                },
                onAdNotAvailable = {
                    adClosed = true
                    onAdNotAvailable()
                },
                onAdClicked = {
                    adClosed = true
                    onAdClicked()
                }
            )
        }
        loadingContent()
    } else {
        content()
    }
}