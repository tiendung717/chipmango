package io.chipmango.ad.interstitial

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import dagger.hilt.android.qualifiers.ApplicationContext
import io.chipmango.ad.request.AdRequestFactory
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InterstitialAdRepo @Inject constructor(@ApplicationContext private val context: Context) {

    private var mapOfAds = mutableMapOf<String, InterstitialAd?>()

    fun setInterstitialAd(adUnitId: String, ad: InterstitialAd?) {
        mapOfAds[adUnitId] = ad
    }

    fun loadInterstitialAd(adUnitId: String) {
        InterstitialAd.load(
            context,
            adUnitId,
            AdRequestFactory.create(),
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(error: LoadAdError) {
                    setInterstitialAd(adUnitId, null)
                }

                override fun onAdLoaded(ad: InterstitialAd) {
                    setInterstitialAd(adUnitId, ad)
                }
            }
        )
    }

    suspend fun showInterstitialAd(
        activity: Activity,
        adUnitId: String,
        onAdDismissed: () -> Unit,
        onAdClicked: () -> Unit,
        onAdFailedToShow: () -> Unit,
        onAdNotAvailable: () -> Unit
    ) {
        val interstitialAd = mapOfAds[adUnitId]
        if (interstitialAd != null) {
            val fullContentCallback = object : FullScreenContentCallback() {
                override fun onAdClicked() {
                    super.onAdClicked()
                    onAdClicked()
                }

                override fun onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent()
                    setInterstitialAd(adUnitId, null)
                    onAdDismissed()
                }

                override fun onAdFailedToShowFullScreenContent(error: AdError) {
                    super.onAdFailedToShowFullScreenContent(error)
                    setInterstitialAd(adUnitId, null)
                    onAdFailedToShow()
                }
            }
            interstitialAd.fullScreenContentCallback = fullContentCallback
            interstitialAd.show(activity)
        } else {
            loadInterstitialAd(adUnitId)
            onAdNotAvailable()
        }
    }
}