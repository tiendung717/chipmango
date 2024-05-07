package io.chipmango.ad

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.ads.nativead.NativeAd
import io.chipmango.ad.banner.AdBanner
import io.chipmango.ad.native.AdNative
import io.chipmango.ad.native.TemplateNativeBanner
import io.chipmango.ad.repo.ChipmangoAdViewModel

@Composable
fun ChipmangoBannerAd(
    modifier: Modifier = Modifier,
    adViewModel: ChipmangoAdViewModel = hiltViewModel(),
    isTestAd: Boolean,
    isPremium: Boolean,
    adUnitId: String
) {
    val adInitialized by remember { adViewModel.isAdInitialized() }.collectAsStateWithLifecycle(initialValue = false)

    if (!isPremium && adInitialized) {
        AdBanner(modifier = modifier, adUnitId = adUnitId, isTestAd = isTestAd)
    }
}

@Composable
fun ChipmangoNativeAd(
    modifier: Modifier = Modifier,
    adViewModel: ChipmangoAdViewModel = hiltViewModel(),
    isTestAd: Boolean,
    isPremium: Boolean,
    darkMode: Boolean,
    adUnitId: String,
    containerColor: Color = Color.Transparent,
    borderColor: Color = Color.Transparent,
    shape: Shape = RectangleShape,
    adLayout: @Composable (Boolean, NativeAd) -> Unit = { darkMode, ad ->
        TemplateNativeBanner(darkMode, ad)
    }
) {
    val adInitialized by remember { adViewModel.isAdInitialized() }.collectAsStateWithLifecycle(initialValue = false)

    if (!isPremium && adInitialized) {
        AdNative(
            modifier = modifier,
            adUnit = adUnitId,
            adLayout = adLayout,
            isDarkMode = darkMode,
            isTestAd = isTestAd,
            containerColor = containerColor,
            shape = shape,
            borderColor = borderColor
        )
    }
}
