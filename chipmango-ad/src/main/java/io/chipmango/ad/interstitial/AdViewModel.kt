package io.chipmango.ad.interstitial

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

sealed class AdControlEvent {
    data object None : AdControlEvent()
    data class ShowInterstitialAd(val timestamp: Long = Random.nextLong()) : AdControlEvent()
}

@HiltViewModel
class InterstitialAdViewModel @Inject constructor(
    private val adRepo: InterstitialAdRepo
) : ViewModel() {
    private val adControlEvent = MutableStateFlow<AdControlEvent>(AdControlEvent.None)
    private var job: Job? = null

    fun fetchAds(vararg adUnitList: String) {
        viewModelScope.launch {
            adUnitList.forEach { adUnitId ->
                adRepo.loadInterstitialAd(adUnitId)
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
            adRepo.showInterstitialAd(
                activity = activity,
                adUnitId = adUnitId,
                onAdDismissed = onAdClosed,
                onAdFailedToShow = onAdFailedToShow,
                onAdNotAvailable = onAdNotAvailable
            )
        }
    }

    fun showInterstitialAd() {
        adControlEvent.value = AdControlEvent.ShowInterstitialAd()
    }

    fun handleAdControlEvent(handler: (AdControlEvent) -> Unit) {
        job = viewModelScope.launch {
            adControlEvent.collect { event ->
                handler(event)
            }
        }
    }

}