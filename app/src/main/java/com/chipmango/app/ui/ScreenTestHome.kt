package com.chipmango.app.ui

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ScreenTestHome(onAccountClicked: () -> Unit) {
    Button(onClick = onAccountClicked) {
        Text(text = "Navigate to Account screen")
    }
}