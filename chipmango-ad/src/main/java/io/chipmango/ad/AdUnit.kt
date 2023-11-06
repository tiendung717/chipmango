package io.chipmango.ad

abstract class AdUnit(val unitId: String)

internal data object TestBanner : AdUnit("ca-app-pub-3940256099942544/6300978111")
internal data object TestNative : AdUnit("ca-app-pub-3940256099942544/2247696110")
internal data object TestNativeVideo : AdUnit("ca-app-pub-3940256099942544/1044960115")
internal data object TestInterstitial : AdUnit("ca-app-pub-3940256099942544/1033173712")
internal data object TestInterstitialVideo : AdUnit("ca-app-pub-3940256099942544/8691691433")
internal data object TestAppOpen : AdUnit("ca-app-pub-3940256099942544/3419835294")
internal data object TestRewarded : AdUnit("ca-app-pub-3940256099942544/5224354917")
internal data object TestRewardedInterstitial : AdUnit("ca-app-pub-3940256099942544/5354046379")