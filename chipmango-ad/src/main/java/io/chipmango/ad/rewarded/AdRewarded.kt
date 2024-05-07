package io.chipmango.ad.rewarded

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import io.chipmango.ad.request.AdRequestFactory
import io.chipmango.ad.AdUnit
import io.chipmango.ad.TestRewarded

@Composable
fun rememberRewardedAd(
    key: Any?,
    context: Context,
    isTestAd: Boolean,
    isPremium: Boolean,
    adUnit: AdUnit,
    onAdLoadFailed: () -> Unit
): State<RewardedAd?> = remember(key) {
    val rewardedAd = mutableStateOf<RewardedAd?>(null)

    if (!isPremium) {
        RewardedAd.load(
            context,
            if (isTestAd) TestRewarded.unitId else adUnit.unitId,
            AdRequestFactory.create(),
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    rewardedAd.value = null
                    onAdLoadFailed()
                }

                override fun onAdLoaded(ad: RewardedAd) {
                    rewardedAd.value = ad
                }
            })
    }

    rewardedAd
}
