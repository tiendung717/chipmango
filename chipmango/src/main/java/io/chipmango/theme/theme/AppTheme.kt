package io.chipmango.theme.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import io.chipmango.theme.colors.LocalColorSet
import io.chipmango.theme.colors.MaterialDarkColors
import io.chipmango.theme.colors.MaterialLightColors
import io.chipmango.theme.colors.ThemeColor
import io.chipmango.theme.di.fontFamily
import io.chipmango.theme.di.themeColor

@Composable
fun AppTheme(
    useDarkTheme: Boolean,
    themeColors: ThemeColor = LocalContext.current.themeColor(),
    fontFamily: FontFamily = LocalContext.current.fontFamily(),
    content: @Composable () -> Unit
) {
    val colors by remember(useDarkTheme) {
        derivedStateOf {
            if (useDarkTheme) themeColors.dark else themeColors.light
        }
    }

    CompositionLocalProvider(LocalColorSet provides colors) {
        MaterialTheme(
            colorScheme = if (useDarkTheme) MaterialDarkColors else MaterialLightColors,
            content = content,
            typography = MaterialTheme.typography.copy(
                displayLarge = MaterialTheme.typography.displayLarge.copy(
                    fontFamily = fontFamily
                ),
                displayMedium = MaterialTheme.typography.displayMedium.copy(
                    fontFamily = fontFamily
                ),
                displaySmall = MaterialTheme.typography.displaySmall.copy(
                    fontFamily = fontFamily
                ),
                headlineLarge = MaterialTheme.typography.headlineLarge.copy(
                    fontFamily = fontFamily
                ),
                headlineMedium = MaterialTheme.typography.headlineMedium.copy(
                    fontFamily = fontFamily
                ),
                headlineSmall = MaterialTheme.typography.headlineSmall.copy(
                    fontFamily = fontFamily
                ),
                titleLarge = MaterialTheme.typography.titleLarge.copy(
                    fontFamily = fontFamily
                ),
                titleMedium = MaterialTheme.typography.titleMedium.copy(
                    fontFamily = fontFamily
                ),
                titleSmall = MaterialTheme.typography.titleSmall.copy(
                    fontFamily = fontFamily
                ),
                bodyLarge = MaterialTheme.typography.bodyLarge.copy(
                    fontFamily = fontFamily
                ),
                bodyMedium = MaterialTheme.typography.bodyMedium.copy(
                    fontFamily = fontFamily
                ),
                bodySmall = MaterialTheme.typography.bodySmall.copy(
                    fontFamily = fontFamily
                ),
                labelLarge = MaterialTheme.typography.labelLarge.copy(
                    fontFamily = fontFamily
                ),
                labelMedium = MaterialTheme.typography.labelMedium.copy(
                    fontFamily = fontFamily
                ),
                labelSmall = MaterialTheme.typography.labelSmall.copy(
                    fontFamily = fontFamily
                )
            )
        )
    }
}