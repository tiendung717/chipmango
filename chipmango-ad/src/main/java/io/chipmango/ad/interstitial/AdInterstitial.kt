package io.chipmango.ad.interstitial

import android.app.Activity
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import io.chipmango.ad.request.AdRequestFactory

@Composable
fun InterstitialAd(
    adUnitId: String,
    onAdLoadFailed: () -> Unit,
    onAdClosed: () -> Unit
) {
    val activity = LocalContext.current as Activity
    val viewModel = hiltViewModel<InterstitialAdViewModel>()
    val ad by viewModel.interstitialAd.collectAsStateWithLifecycle()

    DisposableEffect(Unit) {
        viewModel.loadInterstitialAd(
            context = activity,
            adUnitId = adUnitId,
            onAdClosed = onAdClosed,
            onAdLoadFailed = onAdLoadFailed
        )
        onDispose {
            ad?.fullScreenContentCallback = null
        }
    }

    LaunchedEffect(ad) {
        ad?.show(activity)
    }
}

@Composable
fun rememberInterstitialAd(
    key: Any?,
    context: Context,
    adUnitId: String,
    onAdLoadFailed: () -> Unit,
    onAdClosed: () -> Unit
): State<InterstitialAd?> = remember(key) {
    val interstitialAd = mutableStateOf<InterstitialAd?>(null)
    val fullContentCallback = object : FullScreenContentCallback() {
        override fun onAdClicked() {
            super.onAdClicked()
            onAdClosed()
        }

        override fun onAdDismissedFullScreenContent() {
            super.onAdDismissedFullScreenContent()
            interstitialAd.value = null
            onAdClosed()
        }

        override fun onAdFailedToShowFullScreenContent(error: AdError) {
            super.onAdFailedToShowFullScreenContent(error)
            interstitialAd.value = null
            onAdLoadFailed()
        }
    }

    InterstitialAd.load(
        context,
        adUnitId,
        AdRequestFactory.create(),
        object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(error: LoadAdError) {
                interstitialAd.value?.fullScreenContentCallback = null
                interstitialAd.value = null
                onAdLoadFailed()
            }

            override fun onAdLoaded(ad: InterstitialAd) {
                interstitialAd.value = ad
                interstitialAd.value?.fullScreenContentCallback = fullContentCallback
            }
        }
    )
    
    interstitialAd
}