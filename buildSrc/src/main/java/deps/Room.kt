package deps

object Room : Dependency() {

    object Versions {
        const val room = "2.6.0"
    }

    private const val runtime = "androidx.room:room-runtime:${Versions.room}"
    private const val compiler = "androidx.room:room-compiler:${Versions.room}"
    private const val ktx = "androidx.room:room-ktx:${Versions.room}"

    override fun implementations() = listOf<String>(
        ktx,
        runtime
    )

    override fun kapt() = listOf(
        compiler
    )
}