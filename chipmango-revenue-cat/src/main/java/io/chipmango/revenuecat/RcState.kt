package io.chipmango.revenuecat

import com.revenuecat.purchases.Offering

sealed class RcState {
    data object Loading : RcState()
    data class Error(val message: String) : RcState()
    data class Success(
        val offers: List<RcOffer>
    ) : RcState()
}

data class RcOffer(
    val offering: Offering,
    val title: String,
    val subtitle: String
)