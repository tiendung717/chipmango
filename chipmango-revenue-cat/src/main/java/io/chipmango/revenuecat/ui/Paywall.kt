package io.chipmango.revenuecat.ui

import android.os.CountDownTimer
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.TextButton
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.chipmango.revenuecat.domain.UpgradePlan
import io.chipmango.revenuecat.R

@Composable
internal fun Paywall(
    colors: PaywallColors = PaywallDefaultStyle.colors(),
    texts: PaywallTexts = PaywallDefaultStyle.texts(),
    textStyles: PaywallTextStyles = PaywallDefaultStyle.textStyles(),
    shapes: PaywallShapes = PaywallDefaultStyle.shapes(),
    currentOption: UpgradePlan?,
    options: List<UpgradePlan>,
    onRestoreClick: () -> Unit,
    onPurchaseClick: () -> Unit,
    onOptionSelected: (UpgradePlan) -> Unit,
    showcaseContent: @Composable () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = colors.containerColor
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(bottom = it.calculateBottomPadding()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                showcaseContent()
            }

            item {
                UpgradePlans(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    currentOption = currentOption,
                    onOptionSelected = onOptionSelected,
                    shape = shapes.optionShape,
                    containerColor = colors.optionContainerColor,
                    activeContainerColor = colors.optionActiveContainerColor,
                    titleTextColor = colors.optionTitleTextColor,
                    activeTextColor = colors.optionActiveTextColor,
                    subtitleTextColor = colors.optionSubtitleTextColor,
                    activeSubtitleTextColor = colors.optionActiveSubtitleTextColor,
                    priceTextColor = colors.optionPriceTextColor,
                    activePriceTextColor = colors.optionActivePriceTextColor,
                    borderColor = colors.optionActiveBorderColor,
                    titleTextStyle = textStyles.optionTitleTextStyle,
                    subtitleTextStyle = textStyles.optionSubtitleTextStyle,
                    priceTextStyle = textStyles.optionPriceTextStyle,
                    discountContainerColor = colors.optionDiscountContainerColor,
                    discountContentColor = colors.optionDiscountContentColor,
                    discountTextStyle = textStyles.optionDiscountTextStyle,
                    options = options.toTypedArray()
                )
            }

            item {
                DashedDivider(
                    color = colors.dashedDividerColor,
                    thickness = 1.dp,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                CtaButtons(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    ctaPurchaseLabel = texts.ctaPurchaseText,
                    ctaRestoreLabel = texts.ctaRestoreText.uppercase(),
                    policyText = texts.policyText,
                    onPurchaseClick = onPurchaseClick,
                    onRestoreClick = onRestoreClick,
                    policyTextColor = colors.policyTextColor,
                    ctaPurchaseColor = ButtonDefaults.buttonColors(
                        containerColor = colors.ctaPurchaseContainerColor,
                        contentColor = colors.ctaPurchaseTextColor
                    ),
                    ctaRestoreColors = ButtonDefaults.textButtonColors(
                        contentColor = colors.ctaRestoreTextColor
                    ),
                    ctaPurchaseTextStyle = textStyles.ctaPurchaseTextStyle,
                    ctaRestoreTextStyle = textStyles.ctaRestoreTextStyle,
                    policyTextStyle = textStyles.policyTextStyle,
                    ctaRestoreShape = shapes.ctaShape,
                    ctaPurchaseShape = shapes.ctaShape
                )
            }
        }
    }
}

@Composable
private fun Policy(
    modifier: Modifier,
    text: String = stringResource(id = R.string.payment_policy),
    policyTextColor: Color,
    policyTextStyle: TextStyle,
) {
    Text(
        modifier = modifier,
        text = text,
        style = policyTextStyle,
        color = policyTextColor
    )
}

@Composable
private fun Header(
    modifier: Modifier,
    text: String,
    headerTextColor: Color,
    textStyle: TextStyle
) {
    Text(
        modifier = modifier,
        text = text,
        style = textStyle,
        color = headerTextColor,
        maxLines = 2,
        minLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun CtaButtons(
    modifier: Modifier,
    ctaPurchaseLabel: String,
    ctaRestoreLabel: String,
    policyText: String,
    ctaPurchaseColor: ButtonColors = ButtonDefaults.buttonColors(),
    ctaRestoreColors: ButtonColors = ButtonDefaults.textButtonColors(),
    ctaPurchaseShape: Shape = ButtonDefaults.shape,
    ctaRestoreShape: Shape = ButtonDefaults.textShape,
    policyTextColor: Color,
    ctaPurchaseTextStyle: TextStyle,
    ctaRestoreTextStyle: TextStyle,
    policyTextStyle: TextStyle,
    onPurchaseClick: () -> Unit,
    onRestoreClick: () -> Unit
) {
    Column(
        modifier = modifier,
    ) {
        // Button to purchase
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onPurchaseClick,
            colors = ctaPurchaseColor,
            shape = ctaPurchaseShape,
            contentPadding = PaddingValues(
                horizontal = 12.dp,
                vertical = 16.dp
            )
        ) {
            Text(
                modifier = Modifier,
                text = ctaPurchaseLabel.uppercase(),
                style = ctaPurchaseTextStyle
            )
        }

        // Button to restore
        TextButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = onRestoreClick,
            colors = ctaRestoreColors,
            shape = ctaRestoreShape,
            contentPadding = PaddingValues(
                horizontal = 12.dp,
                vertical = 16.dp
            )
        ) {
            Text(
                modifier = Modifier,
                text = ctaRestoreLabel,
                style = ctaRestoreTextStyle,
            )
        }

        Policy(
            modifier = Modifier.fillMaxWidth(),
            policyTextColor = policyTextColor,
            text = policyText,
            policyTextStyle = policyTextStyle
        )
    }
}


@Composable
private fun UpgradePlans(
    modifier: Modifier,
    currentOption: UpgradePlan?,
    onOptionSelected: (UpgradePlan) -> Unit,
    shape: Shape,
    containerColor: Color,
    activeContainerColor: Color,
    titleTextColor: Color,
    activeTextColor: Color,
    subtitleTextColor: Color,
    activeSubtitleTextColor: Color,
    discountContainerColor: Color,
    discountContentColor: Color,
    priceTextColor: Color,
    activePriceTextColor: Color,
    borderColor: Color,
    titleTextStyle: TextStyle,
    subtitleTextStyle: TextStyle,
    discountTextStyle: TextStyle,
    priceTextStyle: TextStyle,
    vararg options: UpgradePlan
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        options.forEach { option ->
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = shape,
                color = if (option == currentOption) {
                    activeContainerColor
                } else {
                    containerColor
                },
                onClick = { onOptionSelected(option) },
                border = if (option == currentOption) {
                    BorderStroke(
                        width = 2.dp,
                        color = borderColor
                    )
                } else {
                    null
                },
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 16.dp,
                            vertical = 12.dp
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = option.title,
                                style = titleTextStyle,
                                color = if (option == currentOption) {
                                    activeTextColor
                                } else {
                                    titleTextColor
                                }
                            )

                            if (option is UpgradePlan.LifetimeDiscount) {
                                Text(
                                    modifier = Modifier
                                        .background(
                                            color = discountContainerColor,
                                            shape = RoundedCornerShape(100.dp)
                                        )
                                        .padding(
                                            horizontal = 12.dp,
                                            vertical = 4.dp
                                        ),
                                    text = option.discountPercentage,
                                    style = discountTextStyle,
                                    color = discountContentColor
                                )
                            }
                        }

                        if (option is UpgradePlan.LifetimeDiscount) {
                            var duration by remember {
                                mutableStateOf("")
                            }
                            DisposableEffect(Unit) {
                                val timer = object : CountDownTimer(option.discountDuration, 1_000) {
                                    override fun onTick(remainMs: Long) {
                                        duration = "Expires in ${formatMillisToTime(remainMs)}"
                                    }

                                    override fun onFinish() {

                                    }
                                }
                                timer.start()

                                onDispose {
                                    timer.cancel()
                                }
                            }

                            Text(
                                text = duration,
                                style = subtitleTextStyle,
                                color = if (option == currentOption) {
                                    activeSubtitleTextColor
                                } else {
                                    subtitleTextColor
                                }
                            )
                        } else {
                            option.subtitle?.let {
                                Text(
                                    text = it,
                                    style = subtitleTextStyle,
                                    color = if (option == currentOption) {
                                        activeSubtitleTextColor
                                    } else {
                                        subtitleTextColor
                                    }
                                )
                            }
                        }
                    }

                    Column(
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = if (option is UpgradePlan.LifetimeDiscount) {
                                option.discountPrice
                            } else {
                                option.price
                            },
                            style = priceTextStyle,
                            color = if (option == currentOption) {
                                activePriceTextColor
                            } else {
                                priceTextColor
                            }
                        )

                        if (option is UpgradePlan.LifetimeDiscount) {
                            Text(
                                text = option.price,
                                style = priceTextStyle.copy(
                                    textDecoration = TextDecoration.LineThrough
                                ),
                                color = if (option == currentOption) {
                                    activePriceTextColor.copy(0.5f)
                                } else {
                                    priceTextColor.copy(0.5f)
                                }
                            )
                        }

                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PaywallPreview() {
    val options = remember {
        listOf(
            UpgradePlan.Monthly(
                title = "Monthly",
                subtitle = "Billed monthly",
                price = "$9.99/mo"
            ),
            UpgradePlan.Yearly(
                title = "Yearly",
                subtitle = "Billed yearly",
                price = "$99.99/yr"
            ),
            UpgradePlan.Lifetime(
                title = "Lifetime",
                subtitle = "One-time payment",
                price = "$199.99"
            ),
            UpgradePlan.LifetimeDiscount(
                title = "Lifetime",
                subtitle = "One-time payment",
                price = "$99.99",
                discountPrice = "$199.99",
                discountPercentage = "SAVE 50%",
                discountDuration = 50_000
            )
        )
    }

    Paywall(
        onRestoreClick = {},
        onPurchaseClick = {},
        options = options,
        currentOption = options.first(),
        onOptionSelected = {},
        showcaseContent = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun HeaderPreview() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Header(
            modifier = Modifier,
            text = "Start your free trial",
            headerTextColor = Color.Black,
            textStyle = MaterialTheme.typography.titleLarge
        )
        Header(
            modifier = Modifier,
            text = "Pay once, unlock forever",
            headerTextColor = Color.Black,
            textStyle = MaterialTheme.typography.titleLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PolicyPreview() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Policy(
            modifier = Modifier,
            text = stringResource(id = R.string.payment_policy),
            policyTextColor = Color.Black,
            policyTextStyle = MaterialTheme.typography.labelMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CtaButtonsPreview() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        CtaButtons(
            modifier = Modifier,
            ctaPurchaseLabel = "Purchase",
            ctaRestoreLabel = "Already purchased? Restore",
            onPurchaseClick = {},
            onRestoreClick = {},
            ctaPurchaseShape = RoundedCornerShape(8.dp),
            policyTextColor = Color.Gray,
            policyText = stringResource(id = R.string.payment_policy),
            ctaPurchaseTextStyle = MaterialTheme.typography.titleMedium,
            ctaRestoreTextStyle = MaterialTheme.typography.titleMedium,
            policyTextStyle = MaterialTheme.typography.labelMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun UpgradePlansPreview() {
    val options = remember {
        listOf(
            UpgradePlan.Monthly(
                title = "Monthly",
                price = "$9.99/mo"
            ),
            UpgradePlan.Yearly(
                title = "Yearly",
                subtitle = "Billed yearly",
                price = "$99.99/yr"
            ),
            UpgradePlan.Lifetime(
                title = "Lifetime",
                subtitle = "One-time payment",
                price = "$199.99"
            )
        )
    }

    var currentOption by remember(options) {
        mutableStateOf(options.first())
    }
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        UpgradePlans(
            modifier = Modifier,
            currentOption = currentOption,
            onOptionSelected = {
                currentOption = it
            },
            shape = RoundedCornerShape(8.dp),
            containerColor = Color.White,
            activeContainerColor = Color.Gray,
            titleTextColor = Color.Black,
            activeTextColor = Color.White,
            subtitleTextColor = Color.Gray,
            activeSubtitleTextColor = Color.White,
            priceTextColor = Color.Black,
            activePriceTextColor = Color.White,
            borderColor = Color.Black,
            discountContainerColor = Color.Gray,
            discountContentColor = Color.White,
            titleTextStyle = MaterialTheme.typography.titleMedium,
            subtitleTextStyle = MaterialTheme.typography.bodyMedium,
            priceTextStyle = MaterialTheme.typography.titleMedium,
            discountTextStyle = MaterialTheme.typography.labelMedium,
            options = options.toTypedArray()
        )
    }
}

private fun formatMillisToTime(millis: Long): String {
    val totalSeconds = millis / 1000
    val totalMinutes = totalSeconds / 60
    val totalHours = totalMinutes / 60

    val seconds = totalSeconds % 60
    val minutes = totalMinutes % 60
    val hours = totalHours

    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}