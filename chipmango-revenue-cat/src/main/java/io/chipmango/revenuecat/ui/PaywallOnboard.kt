package io.chipmango.revenuecat.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleStartEffect
import com.revenuecat.purchases.CustomerInfo
import io.chipmango.revenuecat.receiver.DiscountReceiver
import io.chipmango.revenuecat.viewmodel.PaywallViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import java.time.Duration
import java.time.ZonedDateTime

@Composable
fun PaywallOnboard(
    paywallViewModel: PaywallViewModel = hiltViewModel<PaywallViewModel>(),
    discountStartTime: ZonedDateTime,
    discountUniqueRequestId: Int,
    discountTitle: String,
    discountMessage: String,
    discountDuration: Duration,
    discountReceiverClass: Class<out DiscountReceiver>,
    shouldTriggerDiscount: (CustomerInfo) -> Boolean = {
        paywallViewModel.userCancelledTrial(it) || paywallViewModel.hasUsedAppForDuration(it, Duration.ofDays(3))
    },
    onLaunchPaywall: () -> Unit
) {
    LifecycleStartEffect(Unit) {
        // Check if it's the first launch
        paywallViewModel.verifyInitialAppLaunch {
            // Check to show discount offer
            paywallViewModel.evaluateDiscountOfferDisplay(
                discountStartTime = discountStartTime,
                discountDuration = discountDuration,
                discountMessage = discountMessage,
                discountTitle = discountTitle,
                discountUniqueRequestId = discountUniqueRequestId,
                discountReceiverClass = discountReceiverClass,
                shouldTriggerDiscount = shouldTriggerDiscount
            )

            // Check to show paywall
            val isPremium = runBlocking {
                paywallViewModel.getPremiumStatusFlow().firstOrNull() ?: false
            }
            if (!paywallViewModel.isPaywallOnboardingShown() && !isPremium) {
                paywallViewModel.setPaywallOnboardingShown()
                onLaunchPaywall()
            }
        }

        onStopOrDispose { }
    }

}