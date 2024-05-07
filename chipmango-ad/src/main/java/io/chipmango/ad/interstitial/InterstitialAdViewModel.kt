package io.chipmango.ad.interstitial

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import io.chipmango.ad.request.AdRequestFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InterstitialAdViewModel @Inject constructor() : ViewModel() {

    val interstitialAd = MutableStateFlow<InterstitialAd?>(null)

    fun loadInterstitialAd(context: Context, adUnitId: String, onAdClosed: () -> Unit, onAdLoadFailed: () -> Unit) {
        viewModelScope.launch {
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
        }
    }
}