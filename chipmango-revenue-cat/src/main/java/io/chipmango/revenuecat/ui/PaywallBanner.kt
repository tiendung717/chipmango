package io.chipmango.revenuecat.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PaywallBanner(
    modifier: Modifier,
    title: String,
    message: String,
    ctaText: String,
    shape: Shape = RoundedCornerShape(8.dp),
    brush: Brush = Brush.horizontalGradient(
        colors = listOf(
            Color(0xFFEC407A),
            Color(0xFFD81B60),
            Color(0xFFAD1457),
        )
    ),
    badgeContainerColor: Color = Color(0xFFE8F5E9),
    badgeContentColor: Color = Color(0xFF4CAF50),
    titleTextColor: Color = Color.White,
    messageTextColor: Color = Color.White,
    ctaTextColor: Color = Color.White,
    titleTextStyle: TextStyle = MaterialTheme.typography.titleLarge,
    messageTextStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    ctaTextStyle: TextStyle = MaterialTheme.typography.titleMedium,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .background(brush = brush, shape = shape)
            .clip(shape)
            .clickable { onClick() }
    ) {
        // Banner content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),

            ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ProBadge(
                    containerColor = badgeContainerColor,
                    contentColor = badgeContentColor,
                )

                Text(
                    text = title,
                    color = titleTextColor,
                    style = titleTextStyle
                )
            }

            Text(
                text = message,
                color = messageTextColor,
                style = messageTextStyle
            )

            Text(
                modifier = Modifier.align(Alignment.End),
                text = ctaText,
                color = ctaTextColor,
                style = ctaTextStyle
            )
        }
    }
}

@Preview
@Composable
fun UpgradeBannerPreview() {
    PaywallBanner(
        modifier = Modifier.fillMaxWidth(),
        title = "Try Premium Free",
        message = "Elevate Your Speech, Master Your Message!\nWhere Professional Videos Begin and Lines Are Never Lost!",
        ctaText = "Learn more"
    ) {}
}