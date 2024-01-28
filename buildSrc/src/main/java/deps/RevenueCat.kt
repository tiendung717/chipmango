package deps

object RevenueCat : Dependency() {
    private const val revenueCat = "com.revenuecat.purchases:purchases:7.3.1"
    private const val revenueCatUi = "com.revenuecat.purchases:purchases-ui:7.3.1"

    override fun implementations() = listOf<String>(
        revenueCat,
        revenueCatUi
    )
}