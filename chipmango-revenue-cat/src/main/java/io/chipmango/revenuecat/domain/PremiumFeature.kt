package io.chipmango.revenuecat.domain

import androidx.compose.ui.graphics.vector.ImageVector

data class PremiumFeature(
    val title: String,
    val subtitle: String? = null,
    val icon: ImageVector? = null
)