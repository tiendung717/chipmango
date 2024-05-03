package io.chipmango.revenuecat.domain

sealed class UpgradePlan(
    open val title: String,
    open val subtitle: String? = null,
    open val price: String
) {
    data class Monthly(
        override val title: String,
        override val subtitle: String? = null,
        override val price: String
    ) : UpgradePlan(title, subtitle, price)

    data class Yearly(
        override val title: String,
        override val subtitle: String? = null,
        override val price: String
    ) : UpgradePlan(title, subtitle, price)

    data class Lifetime(
        override val title: String,
        override val subtitle: String? = null,
        override val price: String
    ) : UpgradePlan(title, subtitle, price)

    data class LifetimeDiscount(
        override val title: String,
        override val subtitle: String? = null,
        override val price: String,
        val discountPrice: String,
        val discountPercentage: String,
        val discountDuration: Long
    ) : UpgradePlan(title, subtitle, price)
}