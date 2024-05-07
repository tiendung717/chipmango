package deps

object Ads : Dependency() {

    object Versions {
        const val admob = "22.4.0"
        const val ump = "2.1.0"
    }

    private const val admob = "com.google.android.gms:play-services-ads:${Versions.admob}"
    private const val ump = "com.google.android.ump:user-messaging-platform:${Versions.ump}"


    override fun implementations() = listOf<String>(
        admob,
        ump
    )
}