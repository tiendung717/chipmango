package io.chipmango.revenuecat.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleStartEffect
import com.revenuecat.purchases.CustomerInfo
import io.chipmango.revenuecat.viewmodel.PaywallViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import java.time.Duration
import java.time.ZonedDateTime

@Composable
fun PaywallOnboard(
    paywallViewModel: PaywallViewModel = hiltViewModel<PaywallViewModel>(),
    discountProductIds: List<String>,
    discountStartTime: ZonedDateTime,
    discountDuration: Duration,
    shouldTriggerDiscount: (CustomerInfo) -> Boolean = {
        paywallViewModel.userCancelledTrial(it) || paywallViewModel.hasUsedAppForDuration(
            it,
            Duration.ofDays(3)
        )
    },
    onLaunchPaywall: () -> Unit
) {
    LifecycleStartEffect(Unit) {
        // Check if it's the first launch
        paywallViewModel.verifyInitialAppLaunch(
            onFirstLaunch = {
                paywallViewModel.loadOfferingAndDiscount(
                    discountProductIds = discountProductIds,
                    onError = { message ->
                        Log.e("PaywallOnboard", message)
                    },
                    onSuccess = { offering, products ->

                    }
                )
            },
            onSubsequentLaunch = {
                // Check to show paywall
                val isPremium = runBlocking {
                    paywallViewModel.getPremiumStatusFlow().firstOrNull() ?: false
                }

                // Check to show discount offer
                paywallViewModel.evaluateDiscountOfferDisplay(
                    discountStartTime = discountStartTime,
                    discountDuration = discountDuration,
                    shouldTriggerDiscount = shouldTriggerDiscount
                )

                paywallViewModel.loadOfferingAndDiscount(
                    discountProductIds = discountProductIds,
                    onError = { message ->
                        Log.e("PaywallOnboard", message)
                    },
                    onSuccess = { offering, products ->
                        if (!paywallViewModel.isPaywallOnboardingShown() && !isPremium) {
                            paywallViewModel.setPaywallOnboardingShown()
                            onLaunchPaywall()
                        }
                    }
                )
            }
        )

        onStopOrDispose { }
    }
}


@Composable
fun PaywallLauncher(
    paywallViewModel: PaywallViewModel = hiltViewModel<PaywallViewModel>(),
    discountProductIds: List<String>,
    discountStartTime: ZonedDateTime,
    discountDuration: Duration,
    shouldTriggerDiscount: (CustomerInfo) -> Boolean = {
        paywallViewModel.userCancelledTrial(it) || paywallViewModel.hasUsedAppForDuration(
            it,
            Duration.ofDays(3)
        )
    },
    onLaunchPaywall: () -> Unit
) {
    LaunchedEffect(Unit) {
        // Check if it's the first launch
        paywallViewModel.verifyInitialAppLaunch(
            onFirstLaunch = {
                paywallViewModel.loadOfferingAndDiscount(
                    discountProductIds = discountProductIds,
                    onError = { message ->
                        Log.e("PaywallOnboard", message)
                    },
                    onSuccess = { offering, products ->

                    }
                )
            },
            onSubsequentLaunch = {
                // Check to show discount offer
                paywallViewModel.evaluateDiscountOfferDisplay(
                    discountStartTime = discountStartTime,
                    discountDuration = discountDuration,
                    shouldTriggerDiscount = shouldTriggerDiscount
                )

                // Check to show paywall
                val isPremium = runBlocking {
                    paywallViewModel.getPremiumStatusFlow().firstOrNull() ?: false
                }

                paywallViewModel.loadOfferingAndDiscount(
                    discountProductIds = discountProductIds,
                    onError = { message ->
                        Log.e("PaywallOnboard", message)
                    },
                    onSuccess = { offering, products ->
                        if (!paywallViewModel.isPaywallOnboardingShown() && !isPremium) {
                            paywallViewModel.setPaywallOnboardingShown()
                            onLaunchPaywall()
                        }
                    }
                )
            }
        )
    }
}