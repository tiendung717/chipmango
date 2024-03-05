package deps

object RevenueCat : Dependency() {
    private const val revenueCat = "com.revenuecat.purchases:purchases:7.5.2"
    private const val revenueCatUi = "com.revenuecat.purchases:purchases-ui:7.5.2"

    override fun implementations() = listOf<String>(
        revenueCat,
        revenueCatUi
    )
}