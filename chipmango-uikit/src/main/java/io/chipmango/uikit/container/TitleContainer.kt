package io.chipmango.uikit.container

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.chipmango.theme.typography.UIKitTypography

@Composable
fun TitleContainer(
    modifier: Modifier,
    title: String,
    contentPadding: PaddingValues,
    titleTextColor: Color,
    titleTextStyle: TextStyle = UIKitTypography.Body1Medium16,
    spacing: Dp = 8.dp,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier.padding(contentPadding),
        verticalArrangement = Arrangement.spacedBy(spacing)
    ) {
        Text(text = title, style = titleTextStyle, color = titleTextColor)

        content()
    }
}