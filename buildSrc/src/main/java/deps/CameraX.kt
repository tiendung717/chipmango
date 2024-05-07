package deps

object CameraX : Dependency() {

    object Versions {
        const val camera_version = "1.4.0-alpha01"
    }

    private const val core = "androidx.camera:camera-core:${Versions.camera_version}"
    private const val camera2 = "androidx.camera:camera-camera2:${Versions.camera_version}"
    private const val cameraLifecycle = "androidx.camera:camera-lifecycle:${Versions.camera_version}"
    private const val cameraVideoCapture = "androidx.camera:camera-video:${Versions.camera_version}"
    private const val cameraExtension = "androidx.camera:camera-extensions:${Versions.camera_version}"
    private const val cameraView = "androidx.camera:camera-view:${Versions.camera_version}"
    private const val concurrentAndroidX = "androidx.concurrent:concurrent-futures-ktx:1.1.0"

    override fun implementations() = listOf<String>(
        core,
        camera2,
        cameraLifecycle,
        cameraVideoCapture,
        cameraExtension,
        cameraView,
        concurrentAndroidX
    )
}