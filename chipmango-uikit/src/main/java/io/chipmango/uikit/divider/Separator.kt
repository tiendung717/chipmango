package io.chipmango.uikit.divider

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Separator(modifier: Modifier = Modifier, thickness: Dp = 1.dp, color: Color) {
    Divider(
        modifier, thickness, color
    )
}