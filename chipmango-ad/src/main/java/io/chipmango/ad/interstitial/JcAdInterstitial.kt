package io.chipmango.ad.interstitial

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import io.chipmango.ad.request.AdRequestFactory
import io.chipmango.ad.AdUnit
import io.chipmango.ad.TestInterstitial
import timber.log.Timber

@Composable
fun rememberInterstitialAd(
    key: Any?,
    context: Context,
    isTestAd: Boolean,
    isPremium: Boolean,
    adUnit: AdUnit,
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
            Timber.tag("nt.dung").e(error.message)
            super.onAdFailedToShowFullScreenContent(error)
            interstitialAd.value = null
            onAdLoadFailed()
        }
    }

    if (!isPremium) {
        InterstitialAd.load(
            context,
            if (isTestAd) TestInterstitial.unitId else adUnit.unitId,
            AdRequestFactory.create(),
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(error: LoadAdError) {
                    Timber.tag("nt.dung").e(error.message)
                    interstitialAd.value?.fullScreenContentCallback = null
                    interstitialAd.value = null
                    onAdLoadFailed()
                }

                override fun onAdLoaded(ad: InterstitialAd) {
                    Timber.tag("nt.dung").e("Ad Loaded!!!!")
                    interstitialAd.value = ad
                    interstitialAd.value?.fullScreenContentCallback = fullContentCallback
                }
            }
        )
    }
    
    interstitialAd
}
