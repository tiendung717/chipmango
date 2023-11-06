package io.chipmango.iap.model

import com.android.billingclient.api.BillingClient.ProductType

data class IapItem(
    val id: String,
    @ProductType val type: String
)