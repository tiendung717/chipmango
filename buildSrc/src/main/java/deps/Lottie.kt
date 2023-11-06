package deps

object Lottie : Dependency() {

    object Versions {
        const val lottie = "5.1.1"
        const val lottie_compose = "1.0.0-rc02-1"
    }

    private const val lottie = "com.airbnb.android:lottie:${Versions.lottie}"
    private const val composeLottie = "com.airbnb.android:lottie-compose:${Versions.lottie_compose}"


    override fun implementations() = listOf(
        lottie,
        composeLottie
    )
}