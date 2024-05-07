package io.chipmango.revenuecat.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material.icons.rounded.Circle
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.chipmango.revenuecat.domain.PremiumFeature

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PaywallShowcase(
    modifier: Modifier,
    title: String,
    features: List<PremiumFeature>,
    brush: Brush,
    proBadgeContainerColor: Color,
    proBadgeContentColor: Color,
    closeContainerColor: Color,
    closeIconColor: Color,
    titleColor: Color,
    onCloseClicked: () -> Unit,
) {
    Column(
        modifier = modifier
            .background(brush = brush)
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProBadge(
                containerColor = proBadgeContainerColor,
                contentColor = proBadgeContentColor
            )

            Spacer(modifier = Modifier.weight(1f))

            IconButton(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(color = closeContainerColor, shape = CircleShape)
                    .padding(6.dp),
                onClick = onCloseClicked
            ) {
                Icon(
                    modifier = Modifier,
                    imageVector = Icons.Rounded.Close,
                    contentDescription = null,
                    tint = closeIconColor
                )
            }
        }

        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = titleColor
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            features.forEach { feature ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(8.dp),
                        imageVector = Icons.Rounded.Circle,
                        contentDescription = null,
                        tint = titleColor
                    )

                    Text(
                        text = feature.title,
                        style = MaterialTheme.typography.bodyLarge,
                        color = titleColor
                    )
                }
            }
        }
    }
}

@Composable
fun ProBadge(
    shape: Shape = RoundedCornerShape(40.dp),
    containerColor: Color,
    contentColor: Color,
    text: String = "PRO"
) {
    Row(
        modifier = Modifier
            .background(color = containerColor, shape = shape)
            .clip(shape)
            .padding(
                horizontal = 12.dp,
                vertical = 6.dp
            ),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(18.dp),
            imageVector = Icons.Rounded.AutoAwesome,
            contentDescription = null,
            tint = contentColor
        )

        Text(
            text = text,
            color = contentColor,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Preview
@Composable
fun ProBadgePreview() {
    ProBadge(
        containerColor = Color(0xFFE8F5E9),
        contentColor = Color(0xFF4CAF50)
    )
}

@Preview(showBackground = true)
@Composable
fun PaywallShowcasePreview() {
    PaywallShowcase(
        modifier = Modifier.fillMaxWidth(),
        title = "Unlock Premium Features",
        features = listOf(
            PremiumFeature(
                title = "Remote control with bluetooth devices",
            ),
            PremiumFeature(
                title = "Video trimming after recording",
            ),
            PremiumFeature(
                title = "Floating Teleprompter",
            ),
            PremiumFeature(
                title = "Import from DOC, PDF, .TXT files",
            ),
            PremiumFeature(
                title = "No Ads",
            ),
        ),
        brush = Brush.verticalGradient(
            colors = listOf(
                Color(0xFF1A1315),
                Color(0xFF000000)
            )
        ),
        proBadgeContainerColor = Color(0xFFE8F5E9),
        proBadgeContentColor = Color(0xFF4CAF50),
        closeContainerColor = Color(0xFFE0E0E0),
        closeIconColor = Color(0xFF212121),
        titleColor = Color(0xFFFFFCFC)
    ) {}
}