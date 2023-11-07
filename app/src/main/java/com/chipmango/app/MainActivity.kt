package com.chipmango.app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import io.chipmango.ad.AdUnit
import io.chipmango.ad.interstitial.rememberInterstitialAd

data object InterstitialAdUnit : AdUnit("")

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var reloadAd by remember {
                mutableLongStateOf(0L)
            }
            val interstitialAd by rememberInterstitialAd(
                key = reloadAd,
                context = LocalContext.current,
                adUnit = InterstitialAdUnit,
                isTestAd = true,
                isPremium = false,
                onAdClosed = {
                    reloadAd = System.currentTimeMillis()
                },
                onAdLoadFailed = {

                }
            )
            Box(Modifier.fillMaxSize()) {
                Button(
                    onClick = {
                        interstitialAd?.show(this@MainActivity)
                    }
                ) {
                    Text("Show")
                }
            }
        }
    }
}