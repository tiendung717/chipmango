package io.chipmango.iap

import io.chipmango.iap.model.IapItem
import io.chipmango.iap.model.PremiumFeature
import kotlinx.coroutines.flow.Flow

interface IapConfiguration {
    fun getPremiumFeatureList(): List<PremiumFeature>
    fun iapLicense(): String
    fun availableIapItems(): List<IapItem>
    fun getMostPopularProductId(): String
    fun savePurchase(productId: String, purchased: Boolean)
    fun isPurchased(productId: String): Flow<Boolean>
    fun isPremium(): Flow<Boolean>
}