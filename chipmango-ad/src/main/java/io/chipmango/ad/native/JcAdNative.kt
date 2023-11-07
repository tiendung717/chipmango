package io.chipmango.ad.native

import android.view.LayoutInflater
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import io.chipmango.ad.request.AdRequestFactory
import io.chipmango.ad.AdUnit
import io.chipmango.ad.R
import io.chipmango.ad.TestBanner
import io.chipmango.ad.TestNative
import timber.log.Timber


@Composable
internal fun JcAdNative(
    modifier: Modifier = Modifier,
    isTestAd: Boolean,
    isDarkMode: Boolean,
    adUnit: AdUnit = TestNative,
    adLayout: @Composable (Boolean, NativeAd) -> Unit = { darkMode, ad ->
        TemplateNativeBanner(modifier, darkMode, ad)
    }
) {
    val context = LocalContext.current
    var nativeAd by remember { mutableStateOf<NativeAd?>(null) }
    var adLoaded by remember { mutableStateOf(false) }
    val ad = remember {
        if (isTestAd) TestNative else adUnit
    }

    val adLoader = remember {
        AdLoader.Builder(context, ad.unitId)
            .forNativeAd { ad: NativeAd ->
                nativeAd = ad
            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Timber.tag("nt.dung").e(adError.message)
                }

                override fun onAdLoaded() {
                    super.onAdLoaded()
                    adLoaded = true
                }
            })
            .withNativeAdOptions(
                NativeAdOptions.Builder()
                    .build()
            )
            .build()
    }

    DisposableEffect(ad) {
        adLoader.loadAd(AdRequestFactory.create())
        onDispose {
            nativeAd?.destroy()
        }
    }

    Box(modifier = modifier) {
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
internal fun TemplateNativeBanner(modifier: Modifier, darkMode: Boolean, ad: NativeAd) {
    AndroidView(
        modifier = modifier.fillMaxWidth(),
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