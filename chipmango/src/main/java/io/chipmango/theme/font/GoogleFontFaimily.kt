package io.chipmango.theme.font

import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import io.chipmango.R

@OptIn(ExperimentalTextApi::class)
internal val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs
)

@OptIn(ExperimentalTextApi::class)
fun googleFontFamily(fontName: String) = FontFamily(
    Font(
        googleFont = GoogleFont(fontName),
        fontProvider = provider,
        weight = FontWeight.ExtraLight
    ),
    Font(
        googleFont = GoogleFont(fontName),
        fontProvider = provider,
        weight = FontWeight.Light
    ),
    Font(
        googleFont = GoogleFont(fontName),
        fontProvider = provider,
        weight = FontWeight.Thin
    ),
    Font(
        googleFont = GoogleFont(fontName),
        fontProvider = provider,
        weight = FontWeight.Normal
    ),
    Font(
        googleFont = GoogleFont(fontName),
        fontProvider = provider,
        weight = FontWeight.Medium
    ),
    Font(
        googleFont = GoogleFont(fontName),
        fontProvider = provider,
        weight = FontWeight.SemiBold
    ),
    Font(
        googleFont = GoogleFont(fontName),
        fontProvider = provider,
        weight = FontWeight.Bold
    ),
    Font(
        googleFont = GoogleFont(fontName),
        fontProvider = provider,
        weight = FontWeight.ExtraBold
    ),
)
