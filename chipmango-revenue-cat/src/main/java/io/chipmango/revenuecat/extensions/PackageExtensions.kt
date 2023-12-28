package io.chipmango.revenuecat.extensions

import com.revenuecat.purchases.Offering
import com.revenuecat.purchases.Package
import com.revenuecat.purchases.PackageType
import io.chipmango.revenuecat.PaywallOffer
import java.util.Locale

fun Package.formattedPrice(locale: Locale = Locale.getDefault()) : String {
    return when (packageType) {
        PackageType.MONTHLY -> formattedPricePerMonth(locale)
        else -> product.price.formatted
    }
}

fun Package.formattedPricePerMonth(locale: Locale = Locale.getDefault()): String {
    return product.formattedPricePerMonth(locale).orEmpty()
}

fun List<Offering>.findPackageByProductId(productId: String): Package? {
    return map { it.availablePackages }.flatten().find { it.product.id == productId }
}

fun List<PaywallOffer>.findPackageWithProductId(productId: String): Package? {
    return map { it.offering }.findPackageByProductId(productId)
}