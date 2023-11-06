package io.chipmango.ad.request

sealed class TestDevice(val id: String) {
    data object Pixel3 : TestDevice("D887B4C580DCD073D702566E04BD2D39")
    data object SerikPixel7Pro : TestDevice("7F143E6FD56329B26F704C2E5C213596")

    companion object {
        internal fun all() = listOf(
            Pixel3,
            SerikPixel7Pro
        )
    }
}
