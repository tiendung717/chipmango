package io.chipmango.ad

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.ads.nativead.NativeAd
import io.chipmango.ad.banner.JcAdBanner
import io.chipmango.ad.native.JcAdNative
import io.chipmango.ad.native.TemplateNativeBanner

@Composable
fun ChipmangoBannerAd(
    modifier: Modifier = Modifier,
    isPremium: Boolean,
    adUnit: AdUnit
) {
    if (!isPremium) {
        JcAdBanner(modifier = modifier, ad = adUnit)
    }
}

@Composable
fun ChipmangoNativeAd(
    modifier: Modifier = Modifier,
    isPremium: Boolean,
    darkMode: Boolean,
    adUnit: AdUnit,
    adLayout: @Composable (Boolean, NativeAd) -> Unit = { darkMode, ad ->
        TemplateNativeBanner(modifier, darkMode, ad)
    }
) {
    if (!isPremium) {
        JcAdNative(
            modifier = modifier,
            adUnit = adUnit,
            adLayout = adLayout,
            isDarkMode = darkMode
        )
    }
}