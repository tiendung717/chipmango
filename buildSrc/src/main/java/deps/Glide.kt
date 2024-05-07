package deps

object Glide : Dependency() {

    object Versions {
        const val glide = "4.13.2"
    }

    private const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    private const val compiler = "com.github.bumptech.glide:compiler:${Versions.glide}"

    override fun implementations() = listOf(
        glide
    )

    override fun kapt() = listOf(
        compiler
    )

}