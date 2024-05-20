package io.chipmango.ad.interstitial

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InterstitialAdViewModel @Inject constructor(
    private val interstitialAdRepo: InterstitialAdRepo
) : ViewModel() {

    fun fetchAds(vararg adUnitList: String) {
        viewModelScope.launch {
            adUnitList.forEach { adUnitId ->
                interstitialAdRepo.loadInterstitialAd(adUnitId)
            }
        }
    }

    fun showInterstitialAd(
        activity: Activity,
        adUnitId: String,
        onAdClosed: () -> Unit,
        onAdFailedToShow: () -> Unit,
        onAdNotAvailable: () -> Unit
    ) {
        viewModelScope.launch {
            interstitialAdRepo.showInterstitialAd(
                activity = activity,
                adUnitId = adUnitId,
                onAdDismissed = onAdClosed,
                onAdFailedToShow = onAdFailedToShow,
                onAdNotAvailable = onAdNotAvailable
            )
        }
    }
}