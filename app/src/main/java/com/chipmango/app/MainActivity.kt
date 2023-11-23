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
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import com.chipmango.app.theme.Colors
import io.chipmango.permission.NotificationRequester
import io.chipmango.theme.colors.LocalColorSet
import io.chipmango.theme.theme.AppTheme
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var darkMode by remember {
                mutableStateOf(false)
            }
            AppTheme(useDarkTheme = darkMode) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            color = Colors.current().background
                        )
                        .clickable { darkMode = !darkMode }
                ) {

                }
            }
        }
    }
}