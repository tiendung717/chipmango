package com.chipmango.app.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import io.chipmango.theme.colors.ColorCollection
import io.chipmango.theme.colors.ColorSet
import io.chipmango.theme.colors.LocalColors

@Composable
fun themeColors() = (LocalColors.current ?: lightColors) as ColorCollection

val lightColors = ColorCollection(
    project = ColorSet(
        Stronger = Color(0xFF005EBA),
        Strong = Color(0xFF0067CB),
        Normal = Color(0xFF0078ED),
        Weak = Color(0xFFD9EBFD),
        Weaker = Color(0xFFE6F2FE)
    ),
    projectInvert = ColorSet(
        Stronger = Color(0xFFE6F2FE),
        Strong = Color(0xFFD9EBFD),
        Normal = Color(0xFF0078ED),
        Weak = Color(0xFF0067CB),
        Weaker = Color(0xFF005EBA)
    ),
    text = ColorSet(
        Stronger = Color(0xFF29293D),
        Strong = Color(0xFF434355),
        Normal = Color(0xFF646473),
        Weak = Color(0xFFA9A9B1),
        Weaker = Color(0xFFCBCDD6),
    ),
    textInvert = ColorSet(
        Stronger = Color(0xFFFFFFFF),
        Strong = Color(0xFFE2E4E9),
        Normal = Color(0xFFCBCDD6),
        Weak = Color(0xFF9195A1),
        Weaker = Color(0xFF62646A),
    ),
    background = ColorSet(
        Stronger = Color(0xFFE4E9F1),
        Strong = Color(0xFFEBEFF5),
        Normal = Color(0xFFF5F7FB),
        Weak = Color(0xFFFAFCFF),
        Weaker = Color(0xFFFFFFFF),
    ),
    backgroundInvert = ColorSet(
        Stronger = Color(0xFF717182),
        Strong = Color(0xFF646473),
        Normal = Color(0xFF343447),
        Weak = Color(0xFF29293D),
        Weaker = Color(0xFF202030),
    ),
    divider = ColorSet(
        Stronger = Color(0xFFCCCED9),
        Strong = Color(0xFFD5D7DE),
        Normal = Color(0xFFF5F7FB),
        Weak = Color(0xFFF8FAFF),
        Weaker = Color(0xFFFBFDFF),
    ),
    dividerInvert = ColorSet(
        Stronger = Color(0xFF5A6072),
        Strong = Color(0xFF535865),
        Normal = Color(0xFF4B4F58),
        Weak = Color(0xFF43454C),
        Weaker = Color(0xFF3B3C40),
    ),
    neutral = ColorSet(
        Stronger = Color(0xFF2B2D3B),
        Strong = Color(0xFF2B2D3B),
        Normal = Color(0xFF646473),
        Weak = Color(0xFFFFFFFF),
        Weaker = Color(0xFFFFFFFF),
    ),
    neutralInvert = ColorSet(
        Stronger = Color(0xFFFFFFFF),
        Strong = Color(0xEBFFFFFF),
        Normal = Color(0xD9FFFFFF),
        Weak = Color(0x33FFFFFF),
        Weaker = Color(0x1AFFFFFF),
    ),
    positive = ColorSet(
        Stronger = Color(0xFF009D66),
        Strong = Color(0xFF00B072),
        Normal = Color(0xFF00C07D),
        Weak = Color(0xFFC4F5E4),
        Weaker = Color(0xFFE0F7EF),
    ),
    positiveInvert = ColorSet(
        Stronger = Color(0xFFE0F7EF),
        Strong = Color(0xFFC4F5E4),
        Normal = Color(0xFF00C07D),
        Weak = Color(0xFF00B072),
        Weaker = Color(0xFF009D66),
    ),
    negative = ColorSet(
        Stronger = Color(0xFFD11C4C),
        Strong = Color(0xFFE01E52),
        Normal = Color(0xFFEE3E3E),
        Weak = Color(0xFFFCE9EE),
        Weaker = Color(0xFFFEEEEE),
    ),
    negativeInvert = ColorSet(
        Stronger = Color(0xFFFEEEEE),
        Strong = Color(0xFFFCE9EE),
        Normal = Color(0xFFEE3E3E),
        Weak = Color(0xFFE01E52),
        Weaker = Color(0xFFD11C4C),
    ),
    warning = ColorSet(
        Stronger = Color(0xFFD58402),
        Strong = Color(0xFFE78F02),
        Normal = Color(0xFFFF9C00),
        Weak = Color(0xFFFFEFD4),
        Weaker = Color(0xFFFFF6E6)
    ),
    warningInvert = ColorSet(
        Stronger = Color(0xFFFFF6E6),
        Strong = Color(0xFFFFEFD4),
        Normal = Color(0xFFFF9C00),
        Weak = Color(0xFFE78F02),
        Weaker = Color(0xFFD58402)
    ),
    textLink = ColorSet(
        Stronger = Color(0xFF005EBA),
        Strong = Color(0xFF0067CB),
        Normal = Color(0xFF0078ED),
        Weak = Color(0xFFD9EBFD),
        Weaker = Color(0xFFEEF5FB),
    ),
    textLinkInvert = ColorSet(
        Stronger = Color(0xFFEEF5FB),
        Strong = Color(0xFFD9EBFD),
        Normal = Color(0xFF0078ED),
        Weak = Color(0xFF0067CB),
        Weaker = Color(0xFF005EBA),
    ),
)

val darkColors = ColorCollection(
    project = ColorSet(
        Stronger = Color(0xFF005EBA),
        Strong = Color(0xFF0067CB),
        Normal = Color(0xFF0078ED),
        Weak = Color(0xFFD9EBFD),
        Weaker = Color(0xFFE6F2FE)
    ),
    projectInvert = ColorSet(
        Stronger = Color(0xFFE6F2FE),
        Strong = Color(0xFFD9EBFD),
        Normal = Color(0xFF0078ED),
        Weak = Color(0xFF0067CB),
        Weaker = Color(0xFF005EBA)
    ),
    text = ColorSet(
        Stronger = Color(0xFFFFFFFF),
        Strong = Color(0xFFE2E4E9),
        Normal = Color(0xFFCBCDD6),
        Weak = Color(0xFF9195A1),
        Weaker = Color(0xFF62646A),
    ),
    textInvert = ColorSet(
        Stronger = Color(0xFFFFFFFF),
        Strong = Color(0xFFE2E4E9),
        Normal = Color(0xFFCBCDD6),
        Weak = Color(0xFF9195A1),
        Weaker = Color(0xFF62646A),
    ),
    background = ColorSet(
        Stronger = Color(0xFF474755),
        Strong = Color(0xFF38384D),
        Normal = Color(0xFF343447),
        Weak = Color(0xFF29293D),
        Weaker = Color(0xFF202030),
    ),
    backgroundInvert = ColorSet(
        Stronger = Color(0xFF333647),
        Strong = Color(0xFF2F3241),
        Normal = Color(0xFF2B2D3B),
        Weak = Color(0xFF272935),
        Weaker = Color(0xFF26262C),
    ),
    divider = ColorSet(
        Stronger = Color(0xFF5A6072),
        Strong = Color(0xFF535865),
        Normal = Color(0xFF4B4F58),
        Weak = Color(0xFF43454C),
        Weaker = Color(0xFF3B3C40),
    ),
    dividerInvert = ColorSet(
        Stronger = Color(0xFF5A6072),
        Strong = Color(0xFF535865),
        Normal = Color(0xFF4B4F58),
        Weak = Color(0xFF43454C),
        Weaker = Color(0xFF3B3C40),
    ),
    neutral = ColorSet(
        Stronger = Color(0xFF26262C),
        Strong = Color(0xEBFFFFFF),
        Normal = Color(0xD9FFFFFF),
        Weak = Color(0x33FFFFFF),
        Weaker = Color(0x1AFFFFFF),
    ),
    neutralInvert = ColorSet(
        Stronger = Color(0xFFFFFFFF),
        Strong = Color(0xEBFFFFFF),
        Normal = Color(0xD9FFFFFF),
        Weak = Color(0x33FFFFFF),
        Weaker = Color(0x1AFFFFFF),
    ),
    positive = ColorSet(
        Stronger = Color(0xFF009D66),
        Strong = Color(0xFF00B072),
        Normal = Color(0xFF00C07D),
        Weak = Color(0xFFC4F5E4),
        Weaker = Color(0xFFE0F7EF),
    ),
    positiveInvert = ColorSet(
        Stronger = Color(0xFFE0F7EF),
        Strong = Color(0xFFC4F5E4),
        Normal = Color(0xFF00C07D),
        Weak = Color(0xFF00B072),
        Weaker = Color(0xFF009D66),
    ),
    negative = ColorSet(
        Stronger = Color(0xFFD11C4C),
        Strong = Color(0xFFE01E52),
        Normal = Color(0xFFEE3E3E),
        Weak = Color(0xFFFCE9EE),
        Weaker = Color(0xFFFEEEEE),
    ),
    negativeInvert = ColorSet(
        Stronger = Color(0xFFFEEEEE),
        Strong = Color(0xFFFCE9EE),
        Normal = Color(0xFFEE3E3E),
        Weak = Color(0xFFE01E52),
        Weaker = Color(0xFFD11C4C),
    ),
    warning = ColorSet(
        Stronger = Color(0xFFD58402),
        Strong = Color(0xFFE78F02),
        Normal = Color(0xFFFF9C00),
        Weak = Color(0xFFFFEFD4),
        Weaker = Color(0xFFFFF6E6)
    ),
    warningInvert = ColorSet(
        Stronger = Color(0xFFFFF6E6),
        Strong = Color(0xFFFFEFD4),
        Normal = Color(0xFFFF9C00),
        Weak = Color(0xFFE78F02),
        Weaker = Color(0xFFD58402)
    ),
    textLink = ColorSet(
        Stronger = Color(0xFF005EBA),
        Strong = Color(0xFF0067CB),
        Normal = Color(0xFF0078ED),
        Weak = Color(0xFFD9EBFD),
        Weaker = Color(0xFFEEF5FB),
    ),
    textLinkInvert = ColorSet(
        Stronger = Color(0xFFEEF5FB),
        Strong = Color(0xFFD9EBFD),
        Normal = Color(0xFF0078ED),
        Weak = Color(0xFF0067CB),
        Weaker = Color(0xFF005EBA),
    ),
)