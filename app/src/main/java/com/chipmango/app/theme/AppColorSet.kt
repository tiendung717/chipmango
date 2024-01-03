package com.chipmango.app.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import io.chipmango.theme.colors.ColorSet
import io.chipmango.theme.colors.LocalColorSet

@Composable
@ReadOnlyComposable
fun themeColors() = (LocalColorSet.current ?: lightColors) as Colors

data class Colors(
    val project: ColorCollection,
    val projectInvert: ColorCollection,
    val text: ColorCollection,
    val textInvert: ColorCollection,
    val background: ColorCollection,
    val backgroundInvert: ColorCollection,
    val divider: ColorCollection,
    val dividerInvert: ColorCollection,
    val neutral: ColorCollection,
    val neutralInvert: ColorCollection,
    val positive: ColorCollection,
    val positiveInvert: ColorCollection,
    val negative: ColorCollection,
    val negativeInvert: ColorCollection,
    val warning: ColorCollection,
    val warningInvert: ColorCollection,
    val textLink: ColorCollection,
    val textLinkInvert: ColorCollection,
) : ColorSet() {
    companion object {
        @Composable
        @ReadOnlyComposable
        fun current() = (LocalColorSet.current ?: lightColors) as Colors
    }
}

data class ColorCollection(
    val Stronger: Color,
    val Strong: Color,
    val Normal: Color,
    val Weak: Color,
    val Weaker: Color
)


val lightColors = Colors(
    project = ColorCollection(
        Stronger = Color(0xFF1C965B),
        Strong = Color(0xFF2FC77E),
        Normal = Color(0xFF32B777),
        Weak = Color(0xFFE6F2FE),
        Weaker = Color(0xFFE6F2FE)
    ),
    projectInvert = ColorCollection(
        Stronger = Color(0xFFE6F2FE),
        Strong = Color(0xFFE6F2FE),
        Normal = Color(0xFF32B777),
        Weak = Color(0xFF2FC77E),
        Weaker = Color(0xFF1C965B)
    ),
    text = ColorCollection(
        Stronger = Color(0xFF1A1D26),
        Strong = Color(0xFF2A2F3D),
        Normal = Color(0xFF4D5364),
        Weak = Color(0xFF6E7489),
        Weaker = Color(0xFFC6C9D2),
    ),
    textInvert = ColorCollection(
        Stronger =Color(0xFFFFFFFF),
        Strong =Color(0xFFE2E4E9),
        Normal =Color(0xFFC6C9D2),
        Weak =Color(0xFF9195A1),
        Weaker =Color(0xFF62646A),
    ),
    background = ColorCollection(
        Stronger = Color(0xFFE4E9F1),
        Strong = Color(0xFFEBEFF5),
        Normal = Color(0xFFF7F7F7),
        Weak = Color(0xFFFCFCFC),
        Weaker = Color(0xFFFFFFFF),
    ),
    backgroundInvert = ColorCollection(
        Stronger = Color(0xFF333647),
        Strong = Color(0xFF2F3241),
        Normal = Color(0xFF2B2D3B),
        Weak = Color(0xFF272935),
        Weaker = Color(0xFF26262C),
    ),
    divider = ColorCollection(
        Stronger = Color(0xFFCCCFD7),
        Strong = Color(0xFFEBEBEB),
        Normal = Color(0xFFF4F4F4),
        Weak = Color(0xFFEFEFF1),
        Weaker = Color(0xFFFAFAFA),
    ),
    dividerInvert = ColorCollection(
        Stronger = Color(0xFF5A6072),
        Strong = Color(0xFF535865),
        Normal = Color(0xFF4B4F58),
        Weak = Color(0xFF43454C),
        Weaker = Color(0xFF3B3C40),
    ),
    neutral = ColorCollection(
        Stronger = Color(0xFFFFFFFF),
        Strong = Color(0xFF26262C),
        Normal = Color(0xFF333647),
        Weak = Color(0xFFFFFFFF),
        Weaker = Color(0xFF26262C),
    ),
    neutralInvert = ColorCollection(
        Stronger = Color(0xFFFFFFFF),
        Strong = Color(0xEBFFFFFF),
        Normal = Color(0xD9FFFFFF),
        Weak = Color(0x33FFFFFF),
        Weaker = Color(0x1AFFFFFF),
    ),
    positive = ColorCollection(
        Stronger = Color(0xFF179470),
        Strong = Color(0xFF00B072),
        Normal = Color(0xFF28C195),
        Weak = Color(0xFFC4F5E4),
        Weaker = Color(0xFFD9EEEA),
    ),
    positiveInvert = ColorCollection(
        Stronger = Color(0xFFD9EEEA),
        Strong = Color(0xFFC4F5E4),
        Normal = Color(0xFF28C195),
        Weak = Color(0xFF00B072),
        Weaker = Color(0xFF179470),
    ),
    negative = ColorCollection(
        Stronger = Color(0xFFD11C4C),
        Strong = Color(0xFFDB2E2E),
        Normal = Color(0xFFF95C60),
        Weak = Color(0xFFFBDCDC),
        Weaker = Color(0xFFFEEEEE),
    ),
    negativeInvert = ColorCollection(
        Stronger = Color(0xFFFEEEEE),
        Strong = Color(0xFFFBDCDC),
        Normal = Color(0xFFF95C60),
        Weak = Color(0xFFDB2E2E),
        Weaker = Color(0xFFD11C4C),
    ),
    warning = ColorCollection(
        Stronger = Color(0xFFFF9C00),
        Strong = Color(0xFFE78F02),
        Normal = Color(0xFFFFDCA1),
        Weak = Color(0xFFFFEFD4),
        Weaker = Color(0xFFFFF6E6)
    ),
    warningInvert = ColorCollection(
        Stronger = Color(0xFFFFF6E6),
        Strong = Color(0xFFFFEFD4),
        Normal = Color(0xFFFFDCA1),
        Weak = Color(0xFFE78F02),
        Weaker = Color(0xFFFF9C00)
    ),
    textLink = ColorCollection(
        Stronger = Color(0xFF005EBA),
        Strong = Color(0xFF0067CB),
        Normal = Color(0xFF0078ED),
        Weak = Color(0xFFD9EBFD),
        Weaker = Color(0xFFEEF5FB),
    ),
    textLinkInvert = ColorCollection(
        Stronger = Color(0xFFEEF5FB),
        Strong = Color(0xFFD9EBFD),
        Normal = Color(0xFF0078ED),
        Weak = Color(0xFF0067CB),
        Weaker = Color(0xFF005EBA),
    ),
)

val darkColors = Colors(
    project = ColorCollection(
        Stronger = Color(0xFF1C965B),
        Strong = Color(0xFF2FC77E),
        Normal = Color(0xFF32B777),
        Weak = Color(0xFFE6F2FE),
        Weaker = Color(0xFFE6F2FE)
    ),
    projectInvert = ColorCollection(
        Stronger = Color(0xFFE6F2FE),
        Strong = Color(0xFFE6F2FE),
        Normal = Color(0xFF32B777),
        Weak = Color(0xFF2FC77E),
        Weaker = Color(0xFF1C965B)
    ),
    text = ColorCollection(
        Stronger = Color(0xFFFFFFFF),
        Strong = Color(0xFFE2E4E9),
        Normal = Color(0xFFC6C9D2),
        Weak = Color(0xFFC8C8CC),
        Weaker = Color(0xFF62646A),
    ),
    textInvert = ColorCollection(
        Stronger = Color(0xFFFFFFFF),
        Strong = Color(0xFFE2E4E9),
        Normal = Color(0xFFC6C9D2),
        Weak = Color(0xFFC8C8CC),
        Weaker = Color(0xFF62646A),
    ),
    background = ColorCollection(
        Stronger = Color(0xFF333647),
        Strong = Color(0xFF2F3241),
        Normal = Color(0xFF2B2A3A),
        Weak = Color(0xFF393948),
        Weaker = Color(0xFF474755),
    ),
    backgroundInvert = ColorCollection(
        Stronger = Color(0xFF333647),
        Strong = Color(0xFF2F3241),
        Normal = Color(0xFF2B2D3B),
        Weak = Color(0xFF272935),
        Weaker = Color(0xFF26262C),
    ),
    divider = ColorCollection(
        Stronger = Color(0xFF5A6072),
        Strong = Color(0xFF535865),
        Normal = Color(0xFF4B4F58),
        Weak = Color(0xFF5A5A69),
        Weaker = Color(0xFF3B3C40),
    ),
    dividerInvert = ColorCollection(
        Stronger = Color(0xFFCCCFD7),
        Strong = Color(0xFFEBEBEB),
        Normal = Color(0xFFF4F4F4),
        Weak = Color(0xFFEFEFF1),
        Weaker = Color(0xFFFAFAFA),
    ),
    neutral = ColorCollection(
        Stronger = Color(0xFF26262C),
        Strong = Color(0xEBFFFFFF),
        Normal = Color(0xD9FFFFFF),
        Weak = Color(0x33FFFFFF),
        Weaker = Color(0x1AFFFFFF),
    ),
    neutralInvert = ColorCollection(
        Stronger = Color(0xFFFFFFFF),
        Strong = Color(0xEBFFFFFF),
        Normal = Color(0xD9FFFFFF),
        Weak = Color(0x33FFFFFF),
        Weaker = Color(0x1AFFFFFF),
    ),
    positive = ColorCollection(
        Stronger = Color(0xFF179470),
        Strong = Color(0xFF00B072),
        Normal = Color(0xFF28C195),
        Weak = Color(0xFFC4F5E4),
        Weaker = Color(0xFFD9EEEA),
    ),
    positiveInvert = ColorCollection(
        Stronger = Color(0xFFD9EEEA),
        Strong = Color(0xFFC4F5E4),
        Normal = Color(0xFF28C195),
        Weak = Color(0xFF00B072),
        Weaker = Color(0xFF179470),
    ),
    negative = ColorCollection(
        Stronger = Color(0xFFCB2525),
        Strong = Color(0xFFDB2E2E),
        Normal = Color(0xFFEE3E3E),
        Weak = Color(0xFFFBDCDC),
        Weaker = Color(0xFFFEEEEE),
    ),
    negativeInvert = ColorCollection(
        Stronger = Color(0xFFFFFFFF),
        Strong = Color(0xFFFFFFFF),
        Normal = Color(0xFFFFFFFF),
        Weak = Color(0xFFFFFFFF),
        Weaker = Color(0xFFFFFFFF),
    ),
    warning = ColorCollection(
        Stronger = Color(0xFFD58402),
        Strong = Color(0xFFE78F02),
        Normal = Color(0xFFFF9C00),
        Weak = Color(0xFFFFEFD4),
        Weaker = Color(0xFFFFF6E6)
    ),
    warningInvert = ColorCollection(
        Stronger = Color(0xFFFFFFFF),
        Strong = Color(0xFFFFFFFF),
        Normal = Color(0xFFFFFFFF),
        Weak = Color(0xFFFFFFFF),
        Weaker = Color(0xFFFFFFFF),
    ),
    textLink = ColorCollection(
        Stronger = Color(0xFF005EBA),
        Strong = Color(0xFF0067CB),
        Normal = Color(0xFF0078ED),
        Weak = Color(0xFFD9EBFD),
        Weaker = Color(0xFFEEF5FB),
    ),
    textLinkInvert = ColorCollection(
        Stronger = Color(0xFF005EBA),
        Strong = Color(0xFF0067CB),
        Normal = Color(0xFF0078ED),
        Weak = Color(0xFFFFFFFF),
        Weaker = Color(0xFFE6F2FE),
    ),
)