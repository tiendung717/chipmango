package io.chipmango.revenuecat.ui

import android.app.Activity
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.revenuecat.purchases.CustomerInfo
import com.revenuecat.purchases.Offering
import com.revenuecat.purchases.PackageType
import com.revenuecat.purchases.ProductType
import com.revenuecat.purchases.PurchasesError
import com.revenuecat.purchases.models.StoreProduct
import io.chipmango.revenuecat.PurchaseListener
import io.chipmango.revenuecat.R
import io.chipmango.revenuecat.domain.SkuType
import io.chipmango.revenuecat.domain.UpgradePlan
import io.chipmango.revenuecat.extensions.hasActiveEntitlements
import io.chipmango.revenuecat.viewmodel.PaywallViewModel

@Composable
fun ScreenPurchase(
    discountProductIds: List<String>,
    skuType: (StoreProduct) -> SkuType,
    colors: PaywallColors = PaywallDefaultStyle.colors(),
    texts: PaywallTexts = PaywallDefaultStyle.texts(),
    textStyles: PaywallTextStyles = PaywallDefaultStyle.textStyles(),
    shapes: PaywallShapes = PaywallDefaultStyle.shapes(),
    contentLoading: @Composable () -> Unit,
    contentError: @Composable (String) -> Unit,
    showcaseContent: @Composable () -> Unit
) {
    val activity = LocalContext.current as Activity
    val paywallViewModel = hiltViewModel<PaywallViewModel>()
    var showThanksDialog by remember {
        mutableStateOf(false)
    }

    val purchaseListener = remember {
        object : PurchaseListener {
            override fun onPurchaseCompleted(customerInfo: CustomerInfo) {
                val isPremium = customerInfo.entitlements.hasActiveEntitlements()
                if (isPremium) {
                    showThanksDialog = true
                }
            }

            override fun onPurchaseCancelled() {

            }

            override fun onPurchaseError(error: PurchasesError) {

            }
        }
    }

    if (paywallViewModel.isDiscountExpired()) {
        OfferContainer(
            contentLoading = contentLoading,
            contentError = contentError
        ) { offer: Offering ->
            PaywallPlans(
                colors = colors,
                texts = texts,
                textStyles = textStyles,
                shapes = shapes,
                products = offer.availablePackages,
                purchaseListener = purchaseListener,
                showcaseContent = showcaseContent,
                optionMapper = { packages ->
                    val options = mutableMapOf<UpgradePlan, StoreProduct?>()
                    packages.forEach {
                        when (it.packageType) {
                            PackageType.MONTHLY -> {
                                val option = UpgradePlan.Monthly(
                                    title = "Monthly",
                                    subtitle = getProductDescription(activity, it.product),
                                    price = it.product.price.formatted
                                )
                                options[option] = it.product
                            }

                            PackageType.ANNUAL -> {
                                val option = UpgradePlan.Yearly(
                                    title = "Yearly",
                                    subtitle = getProductDescription(activity, it.product),
                                    price = it.product.price.formatted
                                )
                                options[option] = it.product
                            }

                            PackageType.LIFETIME -> {
                                val option = UpgradePlan.Lifetime(
                                    title = "Lifetime",
                                    subtitle = getProductDescription(activity, it.product),
                                    price = it.product.price.formatted
                                )
                                options[option] = it.product
                            }

                            else -> {}
                        }
                    }
                    options
                },
                initialPlan = {
                    it.keys.find { plan -> plan is UpgradePlan.Monthly }
                }
            )
        }
    } else {
        DiscountProductContainer(
            productIds = discountProductIds,
            contentLoading = contentLoading,
            contentError = contentError
        ) { products: List<StoreProduct> ->
            PaywallPlans(
                colors = colors,
                texts = texts,
                textStyles = textStyles,
                shapes = shapes,
                products = products,
                purchaseListener = purchaseListener,
                showcaseContent = showcaseContent,
                optionMapper = {
                    val options = mutableMapOf<UpgradePlan, StoreProduct?>()
                    var discountLifetime = UpgradePlan.LifetimeDiscount(
                        title = "Lifetime",
                        price = "",
                        discountPrice = "",
                        discountPercentage = "SAVE 50%",
                        discountDuration = paywallViewModel.getRemainingDiscountDuration()
                            .toMillis()
                    )
                    var discountProduct: StoreProduct? = null
                    products.forEach {
                        when (skuType(it)) {
                            SkuType.LifeTimeDiscount -> {
                                discountProduct = it
                                discountLifetime = discountLifetime.copy(
                                    discountPrice = it.price.formatted,
                                    subtitle = getProductDescription(activity, it)
                                )
                            }

                            SkuType.LifeTime -> {
                                discountLifetime = discountLifetime.copy(
                                    price = it.price.formatted,
                                    subtitle = getProductDescription(activity, it)
                                )
                            }

                            SkuType.MonthLy -> {
                                val option = UpgradePlan.Monthly(
                                    title = "Monthly",
                                    subtitle = getProductDescription(activity, it),
                                    price = it.price.formatted
                                )
                                options[option] = it
                            }

                            SkuType.YearLy -> {
                                val option = UpgradePlan.Monthly(
                                    title = "Yearly",
                                    subtitle = getProductDescription(activity, it),
                                    price = it.price.formatted
                                )
                                options[option] = it
                            }

                            else -> {}
                        }
                    }

                    options[discountLifetime] = discountProduct
                    options
                },
                initialPlan = {
                    it.keys.find { plan -> plan is UpgradePlan.LifetimeDiscount }
                }
            )
        }
    }

    if (showThanksDialog) {
        ThanksDialog(onDismissRequest = { showThanksDialog = false }) {
            activity.finish()
        }
    }
}

private fun getProductDescription(context: Context, product: StoreProduct): String {
    val isLifetime = product.type == ProductType.INAPP
    return when {
        isLifetime -> context.getString(R.string.one_time_payment)
        else -> context.getString(R.string.cancel_anytime_auto_renew)
    }
}