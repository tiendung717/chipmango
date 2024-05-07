package io.chipmango.revenuecat

import com.revenuecat.purchases.CustomerInfo
import com.revenuecat.purchases.Package
import com.revenuecat.purchases.PurchasesError

interface PurchaseListener {
    fun onPurchaseCompleted(customerInfo: CustomerInfo)
    fun onPurchaseCancelled()
    fun onPurchaseError(error: PurchasesError)
}
