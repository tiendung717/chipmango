package io.chipmango.iap

import io.chipmango.iap.model.IapItem
import kotlinx.coroutines.flow.Flow

interface IapConfiguration {
    fun iapLicense(): String
    fun availableIapItems(): List<IapItem>
    fun savePurchase(productId: String, purchased: Boolean)
    fun isPurchased(productId: String): Flow<Boolean>
    fun isPremium(): Flow<Boolean>
}