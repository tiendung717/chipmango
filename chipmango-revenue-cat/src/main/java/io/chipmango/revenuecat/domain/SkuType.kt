package io.chipmango.revenuecat.domain

sealed interface SkuType {
    data object MonthLy : SkuType
    data object YearLy : SkuType
    data object LifeTime : SkuType
    data object LifeTimeDiscount : SkuType
}