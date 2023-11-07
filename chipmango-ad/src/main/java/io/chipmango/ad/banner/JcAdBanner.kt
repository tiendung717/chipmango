package io.chipmango.ad.banner

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Point
import android.os.Build
import android.view.LayoutInflater
import android.view.WindowMetrics
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import io.chipmango.ad.request.AdRequestFactory
import io.chipmango.ad.AdUnit
import timber.log.Timber
import io.chipmango.ad.R
import io.chipmango.ad.TestBanner


@SuppressLint("VisibleForTests")
@Composable
internal fun JcAdBanner(
    modifier: Modifier,
    isTestAd: Boolean,
    adUnit: AdUnit,
    onAdFailedToLoad: () -> Unit = {}
) {
    val bannerAd = remember {
        if (isTestAd) TestBanner else adUnit
    }
    val activity = LocalContext.current as ComponentActivity
    val adSize = remember { getAdSize(activity) }
    var adView: AdView? = remember { null }
    var adLoaded by remember { mutableStateOf(false) }
    Box(
        modifier = modifier.height(adSize.height.dp)
    ) {
        if (!adLoaded) {
            AndroidView(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                factory = { context ->
                    LayoutInflater.from(context)
                        .inflate(R.layout.template_native_loading, null, false)
                }
            )
        }

        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = {
                adView = AdView(it)
                    .apply {
                        setAdSize(adSize)
                        adUnitId = bannerAd.unitId
                    }.also {
                        it.adListener = object : AdListener() {
                            override fun onAdFailedToLoad(error: LoadAdError) {
                                Timber.e(error.message)
                                onAdFailedToLoad()
                            }

                            override fun onAdLoaded() {
                                super.onAdLoaded()
                                adLoaded = true
                            }
                        }
                        it.loadAd(AdRequestFactory.create())
                    }
                adView!!
            }
        )
    }

    DisposableEffect(bannerAd) {
        onDispose {
            adView?.destroy()
        }
    }
}

@SuppressLint("VisibleForTests")
private fun getAdSize(activity: Activity): AdSize {
    val adWidthPixels = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val windowMetrics: WindowMetrics = activity.windowManager.currentWindowMetrics
        val bounds = windowMetrics.bounds
        bounds.width().toFloat()
    } else {
        val display = activity.windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        size.x.toFloat()
    }

    val density: Float = activity.resources.displayMetrics.density
    val adWidth = (adWidthPixels / density).toInt()
    return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, adWidth)
}