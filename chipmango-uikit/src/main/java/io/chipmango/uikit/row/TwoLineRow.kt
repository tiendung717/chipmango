package io.chipmango.uikit.row

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import io.chipmango.theme.typography.UIKitTypography

@Composable
fun TwoLineRow(
    modifier: Modifier,
    title: String,
    description: String,
    titleTextStyle: TextStyle = UIKitTypography.Body1Medium16,
    descriptionTextStyle: TextStyle = UIKitTypography.Body2Regular14,
    titleTextColor: Color,
    descriptionTextColor: Color
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = title, style = titleTextStyle, color = titleTextColor)

        Text(text = description, style = descriptionTextStyle, color = descriptionTextColor)
    }
}