package io.chipmango.revenuecat.ui

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import io.chipmango.revenuecat.R

data class PaywallColors(
    val containerColor: Color,
    val headerTextColor: Color,
    val ctaPurchaseContainerColor: Color,
    val ctaPurchaseTextColor: Color,
    val ctaRestoreTextColor: Color,
    val policyTextColor: Color,
    val optionNotAvailableContainerColor: Color,
    val optionContainerColor: Color,
    val optionActiveContainerColor: Color,
    val optionTitleTextColor: Color,
    val optionActiveTextColor: Color,
    val optionSubtitleTextColor: Color,
    val optionActiveSubtitleTextColor: Color,
    val optionPriceTextColor: Color,
    val optionActivePriceTextColor: Color,
    val optionActiveBorderColor: Color,
    val optionDiscountContainerColor: Color,
    val optionDiscountContentColor: Color,
    val dashedDividerColor: Color
)

data class PaywallTexts(
    val headerText: String,
    val ctaPurchaseText: String,
    val ctaRestoreText: String,
    val policyText: String,
)

data class PaywallTextStyles(
    val headerTextStyle: TextStyle,
    val ctaPurchaseTextStyle: TextStyle,
    val ctaRestoreTextStyle: TextStyle,
    val policyTextStyle: TextStyle,
    val optionTitleTextStyle: TextStyle,
    val optionSubtitleTextStyle: TextStyle,
    val optionPriceTextStyle: TextStyle,
    val optionDiscountTextStyle: TextStyle
)

data class PaywallShapes(
    val showcaseShape: Shape,
    val ctaShape: Shape,
    val optionShape: Shape
)

object PaywallDefaultStyle {

    @Composable
    fun colors(
        containerColor: Color = Color.White,
        headerTextColor: Color = Color.Black,
        ctaPurchaseContainerColor: Color = Color.Black,
        ctaPurchaseTextColor: Color = Color.White,
        ctaRestoreTextColor: Color = Color.Black,
        policyTextColor: Color = Color.Gray,
        optionContainerColor: Color = Color.White,
        optionActiveContainerColor: Color = Color.Black,
        optionTitleTextColor: Color = Color.Black,
        optionActiveTextColor: Color = Color.White,
        optionSubtitleTextColor: Color = Color.Gray,
        optionActiveSubtitleTextColor: Color = Color.White,
        optionPriceTextColor: Color = Color.Black,
        optionActivePriceTextColor: Color = Color.White,
        optionActiveBorderColor: Color = Color.Black,
        optionDiscountContainerColor: Color = Color.Black,
        optionDiscountContentColor: Color = Color.White,
        optionNotAvailableContainerColor: Color = Color.Gray,
        dashedDividerColor: Color = Color.Gray
    ) = PaywallColors(
        containerColor = containerColor,
        headerTextColor = headerTextColor,
        ctaPurchaseContainerColor = ctaPurchaseContainerColor,
        ctaPurchaseTextColor = ctaPurchaseTextColor,
        ctaRestoreTextColor = ctaRestoreTextColor,
        policyTextColor = policyTextColor,
        optionContainerColor = optionContainerColor,
        optionActiveContainerColor = optionActiveContainerColor,
        optionTitleTextColor = optionTitleTextColor,
        optionActiveTextColor = optionActiveTextColor,
        optionSubtitleTextColor = optionSubtitleTextColor,
        optionActiveSubtitleTextColor = optionActiveSubtitleTextColor,
        optionPriceTextColor = optionPriceTextColor,
        optionActivePriceTextColor = optionActivePriceTextColor,
        optionActiveBorderColor = optionActiveBorderColor,
        dashedDividerColor = dashedDividerColor,
        optionDiscountContainerColor = optionDiscountContainerColor,
        optionDiscountContentColor = optionDiscountContentColor,
        optionNotAvailableContainerColor = optionNotAvailableContainerColor
    )

    @Composable
    fun texts(
        headerText: String = "Unlock Premium Features",
        ctaPurchaseText: String = "Purchase",
        ctaRestoreText: String = "Restore Purchase",
        policyText: String = stringResource(id = R.string.payment_policy),
    ) = PaywallTexts(
        headerText = headerText,
        ctaPurchaseText = ctaPurchaseText,
        ctaRestoreText = ctaRestoreText,
        policyText = policyText,
    )

    @Composable
    fun textStyles(
        headerTextStyle: TextStyle = MaterialTheme.typography.titleLarge,
        ctaPurchaseTextStyle: TextStyle = MaterialTheme.typography.bodyLarge,
        ctaRestoreTextStyle: TextStyle = MaterialTheme.typography.bodyLarge,
        policyTextStyle: TextStyle = MaterialTheme.typography.labelMedium,
        optionTitleTextStyle: TextStyle = MaterialTheme.typography.bodyLarge,
        optionSubtitleTextStyle: TextStyle = MaterialTheme.typography.bodyMedium,
        optionPriceTextStyle: TextStyle = MaterialTheme.typography.bodyMedium,
        optionDiscountTextStyle: TextStyle = MaterialTheme.typography.labelSmall
    ) = PaywallTextStyles(
        headerTextStyle = headerTextStyle,
        ctaPurchaseTextStyle = ctaPurchaseTextStyle,
        ctaRestoreTextStyle = ctaRestoreTextStyle,
        policyTextStyle = policyTextStyle,
        optionTitleTextStyle = optionTitleTextStyle,
        optionSubtitleTextStyle = optionSubtitleTextStyle,
        optionPriceTextStyle = optionPriceTextStyle,
        optionDiscountTextStyle = optionDiscountTextStyle
    )

    @Composable
    fun shapes(
        showcaseShape: Shape = RoundedCornerShape(8.dp),
        ctaShape: Shape = RoundedCornerShape(8.dp),
        optionShape: Shape = RoundedCornerShape(8.dp)
    ) = PaywallShapes(
        showcaseShape = showcaseShape,
        ctaShape = ctaShape,
        optionShape = optionShape
    )
}