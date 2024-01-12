package com.chipmango.app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.chipmango.app.theme.themeColors
import com.solid.test.BuildConfig
import dagger.hilt.android.AndroidEntryPoint
import io.chipmango.rating.AppRatingDialog
import io.chipmango.rating.viewmodel.RatingViewModel
import io.chipmango.theme.theme.AppTheme
import io.chipmango.theme.typography.UIKitTypography
import com.solid.test.R
import io.chipmango.ad.AdContainerActivity
import io.chipmango.ad.AdUnit
import io.chipmango.ad.ChipmangoAds
import io.chipmango.ad.ChipmangoBannerAd
import io.chipmango.ad.ChipmangoNativeAd
import io.chipmango.uikit.UiKitApp
import io.chipmango.uikit.scaffold.AppScaffold
import javax.inject.Inject

data object BlankAdUnit : AdUnit("")

@AndroidEntryPoint
class MainActivity : AdContainerActivity() {

    private val ratingViewModel: RatingViewModel by viewModels<RatingViewModel>()

    override fun isAdEnabled(): Boolean {
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val darkMode by remember {
                mutableStateOf(false)
            }

            AppTheme(useDarkTheme = darkMode) {
                AppScaffold(
                    containerColor = themeColors().background.Normal,
                    bottomBar = {
                        ChipmangoBannerAd(
                            isTestAd = true,
                            isPremium = false,
                            adUnit = BlankAdUnit
                        )
                    },
                    topBar = {
                        ChipmangoNativeAd(
                            isTestAd = true,
                            isPremium = false,
                            darkMode = false,
                            adUnit = BlankAdUnit
                        )
                    }
                ) {
                    UiKitApp(containerColor = themeColors().background.Normal)
                }
            }
        }
    }
}