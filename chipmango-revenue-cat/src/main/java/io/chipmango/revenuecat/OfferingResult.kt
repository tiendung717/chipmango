package io.chipmango.revenuecat

import com.revenuecat.purchases.Offering
import com.revenuecat.purchases.models.StoreProduct

sealed class OfferingResult {
    data object Loading : OfferingResult()
    data class Error(val message: String) : OfferingResult()
    data class Success(val offer: Offering) : OfferingResult()
}

sealed class ProductsResult {
    data object Loading : ProductsResult()
    data class Error(val message: String) : ProductsResult()
    data class Success(val products: List<StoreProduct>) : ProductsResult()
}