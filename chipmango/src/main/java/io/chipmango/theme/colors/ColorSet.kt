package io.chipmango.theme.colors

import androidx.compose.runtime.staticCompositionLocalOf

abstract class ColorSet

class ThemeColor(
    val light: ColorSet,
    val dark: ColorSet
)

val defaultColorSet: ColorSet? = null
val LocalColorSet = staticCompositionLocalOf { defaultColorSet }