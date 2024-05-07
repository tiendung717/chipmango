package io.chipmango.revenuecat.ui

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.revenuecat.purchases.ProductType
import com.revenuecat.purchases.models.Period
import com.revenuecat.purchases.models.StoreProduct
import io.chipmango.revenuecat.domain.UpgradePlan
import io.chipmango.revenuecat.PurchaseListener
import io.chipmango.revenuecat.R
import io.chipmango.revenuecat.viewmodel.PaywallViewModel

@Composable
internal fun <T> PaywallPlans(
    colors: PaywallColors = PaywallDefaultStyle.colors(),
    texts: PaywallTexts = PaywallDefaultStyle.texts(),
    textStyles: PaywallTextStyles = PaywallDefaultStyle.textStyles(),
    shapes: PaywallShapes = PaywallDefaultStyle.shapes(),
    products: List<T>,
    purchaseListener: PurchaseListener? = null,
    optionMapper: (List<T>) -> Map<UpgradePlan, StoreProduct?>,
    initialPlan: (Map<UpgradePlan, StoreProduct?>) -> UpgradePlan?,
    showcaseContent: @Composable () -> Unit
) {
    val activity = LocalContext.current as Activity
    val paywallViewModel = hiltViewModel<PaywallViewModel>()
    val options = remember(products) { optionMapper(products) }
    var currentOption by remember(options) {
        mutableStateOf(initialPlan(options))
    }

    val ctaText by remember(currentOption) {
        derivedStateOf {
            val product = options[currentOption]
            val isLifetime = product?.type == ProductType.INAPP
            val trialPeriod = product?.subscriptionOptions?.freeTrial?.freePhase?.billingPeriod
            val trialUnit = when (trialPeriod?.unit) {
                Period.Unit.DAY -> "days"
                Period.Unit.WEEK -> "weeks"
                Period.Unit.MONTH -> "months"
                Period.Unit.YEAR -> "years"
                else -> ""
            }
            when {
                isLifetime -> activity.getString(R.string.buy_now)
                trialPeriod != null -> "Start your ${trialPeriod.value}-$trialUnit trial"
                else -> activity.getString(R.string.subscribe_now)
            }
        }
    }

    Paywall(
        onRestoreClick = {
            paywallViewModel.restorePurchase()
        },
        onPurchaseClick = {
            options[currentOption]?.let {
                paywallViewModel.makePurchase(
                    activity = activity,
                    product = it,
                    purchaseListener = purchaseListener
                )
            }
        },
        colors = colors,
        textStyles = textStyles,
        texts = texts.copy(
            ctaPurchaseText = ctaText
        ),
        shapes = shapes,
        showcaseContent = showcaseContent,
        currentOption = currentOption,
        options = options.keys.toList(),
        onOptionSelected = { currentOption = it }
    )
}