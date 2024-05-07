package io.chipmango.ad.native

import android.content.Context
import android.view.LayoutInflater
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.gms.ads.nativead.NativeAdOptions.ADCHOICES_TOP_RIGHT
import io.chipmango.ad.request.AdRequestFactory
import io.chipmango.ad.R
import io.chipmango.ad.TestNative
import timber.log.Timber


@Composable
internal fun AdNative(
    modifier: Modifier = Modifier,
    isTestAd: Boolean,
    isDarkMode: Boolean,
    containerColor: Color,
    shape: Shape,
    borderColor: Color,
    adUnit: String,
    adLayout: @Composable (Boolean, NativeAd) -> Unit = { darkMode, ad ->
        TemplateNativeBanner(darkMode, ad)
    }
) {
    val context = LocalContext.current
    var nativeAd by remember { mutableStateOf<NativeAd?>(null) }
    var adLoaded by remember { mutableStateOf(false) }
    val ad = remember {
        if (isTestAd) TestNative.unitId else adUnit
    }
    val nativeAdLoader = rememberNativeAdListener(
        context = context,
        adUnitId = ad,
        onAdLoadFailed = {
            Timber.tag("nt.dung").e(it.message)
        },
        onAdLoaded = {
            adLoaded = true
        },
        onNativeAdLoaded = {
            nativeAd = it
        }
    )

    DisposableEffect(ad) {
        nativeAdLoader.loadAd(AdRequestFactory.create())
        onDispose {
            nativeAd?.destroy()
        }
    }

    Box(
        modifier = modifier
            .background(containerColor, shape)
            .border(1.dp, borderColor, shape)
            .clip(shape)
            .animateContentSize()
    ) {
        if (!adLoaded) {
            AndroidView(
                modifier = Modifier.fillMaxWidth(),
                factory = { context ->
                    LayoutInflater.from(context)
                        .inflate(R.layout.template_native_loading, null, false)
                }
            )
        } else {
            nativeAd?.let {
                adLayout(isDarkMode, it)
            }
        }
    }
}

@Composable
internal fun TemplateNativeBanner(darkMode: Boolean, ad: NativeAd) {
    AndroidView(
        modifier = Modifier.fillMaxWidth(),
        factory = {
            TemplateNativeAdView(it).apply {
                render(ad, darkMode)
            }
        },
        update = {
            it.render(ad, darkMode)
        }
    )
}

@Composable
private fun rememberNativeAdListener(
    context: Context,
    adUnitId: String,
    onAdLoadFailed: (LoadAdError) -> Unit,
    onAdLoaded: () -> Unit,
    onNativeAdLoaded: (NativeAd) -> Unit
) = remember {
    AdLoader.Builder(context, adUnitId)
        .forNativeAd { ad: NativeAd ->
            onNativeAdLoaded(ad)
        }
        .withAdListener(object : AdListener() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                onAdLoadFailed(adError)
            }

            override fun onAdLoaded() {
                super.onAdLoaded()
                onAdLoaded()
            }
        })
        .withNativeAdOptions(
            NativeAdOptions.Builder()
                .setAdChoicesPlacement(ADCHOICES_TOP_RIGHT)
                .build()
        )
        .build()
}