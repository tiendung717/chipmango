package com.chipmango.app.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import io.chipmango.theme.colors.ColorSet
import io.chipmango.theme.colors.LocalColorSet

data class Colors(
    val background: Color,
    val onBackground: Color
) : ColorSet() {
    companion object {
        @Composable
        @ReadOnlyComposable
        fun current() = (LocalColorSet.current ?: lightColors) as Colors
    }
}

val lightColors = Colors(
    background = Color.Green,
    onBackground = Color.Black
)

val darkColors = Colors(
    background = Color.Red,
    onBackground = Color.White
)
