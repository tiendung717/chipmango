package io.chipmango.ad.interstitial

import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import dagger.hilt.android.qualifiers.ApplicationContext
import io.chipmango.ad.request.AdRequestFactory
import timber.log.Timber
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
                    Timber.tag("nt.dung").e("onAdFailedToLoad: ${error.message}")
                    setInterstitialAd(adUnitId, null)
                }

                override fun onAdLoaded(ad: InterstitialAd) {
                    Timber.tag("nt.dung").e("onAdLoaded (responseId): ${ad.responseInfo.responseId}")
                    setInterstitialAd(adUnitId, ad)
                }
            }
        )
    }

    suspend fun showInterstitialAd(
        activity: Activity,
        adUnitId: String,
        onAdDismissed: () -> Unit,
        onAdFailedToShow: () -> Unit,
        onAdNotAvailable: () -> Unit
    ) {
        val interstitialAd = mapOfAds[adUnitId]
        if (interstitialAd != null) {
            val fullContentCallback = object : FullScreenContentCallback() {
                override fun onAdClicked() {
                    super.onAdClicked()
                }

                override fun onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent()
                    onAdDismissed()
                }

                override fun onAdFailedToShowFullScreenContent(error: AdError) {
                    super.onAdFailedToShowFullScreenContent(error)
                    setInterstitialAd(adUnitId, null)
                    loadInterstitialAd(adUnitId)
                    onAdFailedToShow()
                }

                override fun onAdShowedFullScreenContent() {
                    super.onAdShowedFullScreenContent()
                    loadInterstitialAd(adUnitId)
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