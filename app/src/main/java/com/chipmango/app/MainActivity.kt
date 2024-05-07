package com.chipmango.app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.chipmango.app.navigation.MainNavigation
import dagger.hilt.android.AndroidEntryPoint
import io.chipmango.ad.AdDisplayActivity
import io.chipmango.ad.AdUnit
import io.chipmango.theme.theme.AppTheme

data object BlankAdUnit : AdUnit("")

@AndroidEntryPoint
class MainActivity : AdDisplayActivity() {

    override fun isAdEnabled(): Boolean {
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme(useDarkTheme = true) {
                MainNavigation(navController = rememberNavController())
            }
        }
    }
}