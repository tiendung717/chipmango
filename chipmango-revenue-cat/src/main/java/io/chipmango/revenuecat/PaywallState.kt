package io.chipmango.revenuecat

import com.revenuecat.purchases.Offering

sealed class PaywallState {
    data object Loading : PaywallState()
    data class Error(val message: String) : PaywallState()
    data class Success(
        val offers: List<PaywallOffer>
    ) : PaywallState()
}

data class PaywallOffer(
    val offering: Offering,
    val title: String,
    val subtitle: String
)