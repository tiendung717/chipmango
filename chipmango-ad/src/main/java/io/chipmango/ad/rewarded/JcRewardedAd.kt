package io.chipmango.ad.rewarded

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import io.chipmango.ad.request.AdRequestFactory
import io.chipmango.ad.AdUnit
import io.chipmango.ad.TestRewarded
import timber.log.Timber

@Composable
internal fun JcRewardedAd(
    adUnit: AdUnit = TestRewarded,
    showTimestamp: Long = 0,
    onRewardedAdLoaded: () -> Unit = {},
    onUserEarnedReward: (RewardItem) -> Unit,
    onRewardLoadFailed: () -> Unit = {},
) {
    val activity = LocalContext.current as ComponentActivity
    var rewardedAd: RewardedAd? by remember { mutableStateOf(null) }
    var reload by remember {
        mutableLongStateOf(0L)
    }

    DisposableEffect(reload) {
        RewardedAd.load(
            activity,
            adUnit.unitId,
            AdRequestFactory.create(),
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    rewardedAd = null
                    Timber.e("Reward ad load failed: ${adError.message}")
                }

                override fun onAdLoaded(ad: RewardedAd) {
                    rewardedAd = ad
                    onRewardedAdLoaded()
                }
            })
        onDispose {
            rewardedAd = null
        }
    }

    LaunchedEffect(showTimestamp) {
        if (showTimestamp > 0) {
            if (rewardedAd != null) {
                rewardedAd?.show(activity) { rewardItem ->
                    onUserEarnedReward(rewardItem)
                    reload = System.currentTimeMillis()
                }
            } else {
                onRewardLoadFailed()
                reload = System.currentTimeMillis()
            }
        }
    }
}
