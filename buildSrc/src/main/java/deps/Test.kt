package deps

object Test : Dependency() {

    object Versions {
        const val mockkVersion = "1.13.2"
        const val coreTestingVersion = "2.1.0"
        const val coroutineTestVersion = "1.6.4"
        const val junit = "4.13.2"
    }

    private const val mockk = "io.mockk:mockk-android:${Versions.mockkVersion}"
    private const val coreTest = "androidx.arch.core:core-testing:${Versions.coreTestingVersion}"
    private const val coroutineTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutineTestVersion}"
    private const val junit = "junit:junit:${Versions.junit}"
    private const val junit_ext = "androidx.test.ext:junit-ktx:1.1.3"

    override fun testImplementations() = listOf(
        mockk, coreTest, coroutineTest
    )

    override fun implementations() = listOf(
        junit_ext
    )
}