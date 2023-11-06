package io.chipmango.iap.ext

import com.android.billingclient.api.BillingClient.BillingResponseCode
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.Purchase.PurchaseState
import io.chipmango.iap.ext.Security.verifyPurchase
import timber.log.Timber

internal fun BillingResult.isSuccess() = responseCode == BillingResponseCode.OK

internal fun BillingResult.isUserCancelled() = responseCode == BillingResponseCode.USER_CANCELED

internal fun Purchase.isPurchased() = purchaseState == PurchaseState.PURCHASED

internal fun Purchase.isPending() = purchaseState == PurchaseState.PENDING

internal fun Purchase.isConsumable() = false

internal fun String.verifySignature(purchase: Purchase): Boolean {
    return verifyPurchase(purchase.originalJson, purchase.signature, this)
}

