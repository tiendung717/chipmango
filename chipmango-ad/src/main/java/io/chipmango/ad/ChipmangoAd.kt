package io.chipmango.ad

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import com.google.android.gms.ads.nativead.NativeAd
import io.chipmango.ad.banner.JcAdBanner
import io.chipmango.ad.native.JcAdNative
import io.chipmango.ad.native.TemplateNativeBanner

@Composable
fun ChipmangoBannerAd(
    modifier: Modifier = Modifier,
    isTestAd: Boolean,
    isPremium: Boolean,
    adUnit: AdUnit
) {
    if (!isPremium) {
        JcAdBanner(modifier = modifier, adUnit = adUnit, isTestAd = isTestAd)
    }
}

@Composable
fun ChipmangoNativeAd(
    modifier: Modifier = Modifier,
    isTestAd: Boolean,
    isPremium: Boolean,
    darkMode: Boolean,
    adUnit: AdUnit,
    containerColor: Color = Color.Transparent,
    borderColor: Color = Color.Transparent,
    shape: Shape = RectangleShape,
    adLayout: @Composable (Boolean, NativeAd) -> Unit = { darkMode, ad ->
        TemplateNativeBanner(darkMode, ad)
    }
) {
    if (!isPremium) {
        JcAdNative(
            modifier = modifier,
            adUnit = adUnit,
            adLayout = adLayout,
            isDarkMode = darkMode,
            isTestAd = isTestAd,
            containerColor = containerColor,
            shape = shape,
            borderColor = borderColor
        )
    }
}
