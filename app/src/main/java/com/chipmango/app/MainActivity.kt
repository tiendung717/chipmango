package com.chipmango.app

import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.chipmango.app.theme.Colors
import com.chipmango.app.theme.themeColors
import io.chipmango.ad.AdUnit
import io.chipmango.ad.ChipmangoBannerAd
import io.chipmango.ad.ChipmangoNativeAd
import io.chipmango.permission.NotificationRequester
import io.chipmango.rating.AppRatingDialog
import io.chipmango.theme.colors.LocalColorSet
import io.chipmango.theme.di.themeColor
import io.chipmango.theme.theme.AppTheme
import io.chipmango.uikit.UiKitApp
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val darkMode by remember {
                mutableStateOf(false)
            }
            AppTheme(useDarkTheme = darkMode) {
                UiKitApp(containerColor = themeColors().background.Normal)

                AppRatingDialog {

                }
            }
        }
    }
}