package io.chipmango.revenuecat

import com.revenuecat.purchases.CustomerInfo
import com.revenuecat.purchases.Package
import com.revenuecat.purchases.PurchasesError
import com.revenuecat.purchases.models.StoreProduct

interface PurchaseListener {
    fun onPurchaseCompleted(product: StoreProduct, customerInfo: CustomerInfo)
    fun onPurchaseCancelled()
    fun onPurchaseError(error: PurchasesError)
    fun onRestoreCompleted(customerInfo: CustomerInfo)
}
