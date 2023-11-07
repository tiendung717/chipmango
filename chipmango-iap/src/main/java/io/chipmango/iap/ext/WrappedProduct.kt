package io.chipmango.iap.ext

import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.ProductDetails
import java.time.Period

internal data class WrappedProduct(val product: ProductDetails) {
    fun title() = product.name

    fun description() = product.description

    private fun isSubscription() = product.productType == BillingClient.ProductType.SUBS

    fun offerToken(): String? {
        val originalOfferToken =
            product
                .subscriptionOfferDetails
                ?.firstOrNull { it.offerId == null }
                ?.offerToken
        return product.subscriptionOfferDetails
            ?.firstOrNull { it.offerId != null }
            ?.offerToken
            .orEmpty()
            .ifEmpty { originalOfferToken }
    }

    fun price(): String {
        return if (isSubscription()) subscriptionPrice() else oneTimePrice()
    }

    private fun oneTimePrice() : String {
        return product.oneTimePurchaseOfferDetails?.formattedPrice.orEmpty()
    }

    private fun subscriptionPrice(): String {
        val pricePhase = product.subscriptionOfferDetails
            ?.firstOrNull { it.offerId == null }
            ?.pricingPhases
            ?.pricingPhaseList
            .orEmpty()
            .firstOrNull()

        return pricePhase?.let {
            val billingPeriod = Period.parse(it.billingPeriod)
            val price = it.formattedPrice
            val period = when {
                billingPeriod.months == 1 -> "month"
                billingPeriod.years == 1 -> "year"
                billingPeriod.days == 7 -> "week"
                else -> ""
            }

            listOf(price, period).joinToString("/")
        }.orEmpty()
    }
}