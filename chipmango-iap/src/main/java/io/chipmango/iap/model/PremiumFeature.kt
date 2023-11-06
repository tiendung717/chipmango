package io.chipmango.iap.model

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector

abstract class PremiumFeature(
    val id: String,
    val icon: ImageVector,
    @StringRes val title: Int,
    @StringRes val description: Int,
)