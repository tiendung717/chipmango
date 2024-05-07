package deps

@Suppress("unused")
object Retrofit : Dependency() {
    object Versions {
        const val retrofit = "2.9.0"
        const val okhttp = "4.2.1"
        const val okhttpLog = "5.0.0-alpha.2"
        const val gson = "2.8.6"
        const val stetho = "1.6.0"
    }

    private const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    private const val retrofitGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    private const val okhttp =  "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    private const val okhttpLog =  "com.squareup.okhttp3:logging-interceptor:${Versions.okhttpLog}"
    private const val gson = "com.google.code.gson:gson:${Versions.gson}"
    private const val stetho = "com.facebook.stetho:stetho:${Versions.stetho}"
    private const val stethoOkhttp3 = "com.facebook.stetho:stetho-okhttp3:${Versions.stetho}"

    override fun implementations() = listOf<String>(
        retrofit,
        retrofitGson,
        okhttp,
        okhttpLog,
        gson,
        stetho,
        stethoOkhttp3
    )

    override fun debugImplementations(): List<String> = emptyList()
}