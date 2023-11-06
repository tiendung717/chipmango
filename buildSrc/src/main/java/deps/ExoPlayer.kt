package deps

object ExoPlayer : Dependency() {

    object Versions {
    }

    private const val exoPlayer = "com.google.android.exoplayer:exoplayer:2.19.0"
    private const val media3_exo_player = "androidx.media3:media3-exoplayer:1.1.0"
    private const val media3_ui = "androidx.media3:media3-ui:1.1.0"

    override fun implementations() = listOf(
        media3_exo_player,
        media3_ui
    )
}