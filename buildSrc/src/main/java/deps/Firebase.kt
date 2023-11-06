package deps

object Firebase : Dependency() {

    object Versions {
        const val firebaseBom = "32.2.0"
    }

    private const val firebaseBom = "com.google.firebase:firebase-bom:${Versions.firebaseBom}"
    private const val firebaseCrashlytics = "com.google.firebase:firebase-crashlytics-ktx"
    private const val firebaseAnalytics = "com.google.firebase:firebase-analytics-ktx"
    private const val firebaseAuth = "com.google.firebase:firebase-auth-ktx"
    private const val firebaseDynamicLink = "com.google.firebase:firebase-dynamic-links-ktx"
    private const val firebaseMessage = "com.google.firebase:firebase-messaging"
    private const val firebasePerformance = "com.google.firebase:firebase-perf"
    private const val firestore = "com.google.firebase:firebase-firestore-ktx"
    private const val firebaseRemoteConfig = "com.google.firebase:firebase-config-ktx"


    private const val googlePlayAuth = "com.google.android.gms:play-services-auth:20.4.1"
    private const val playintegrity = "com.google.firebase:firebase-appcheck-playintegrity"
    // Use App Check with the debug provider when running in the debug environment.
    // https://firebase.google.com/docs/app-check/android/debug-provider
    private const val playintegrityDebug = "com.google.firebase:firebase-appcheck-debug"

    override fun implementations() = listOf<String>(
        firebaseCrashlytics,
        firebaseAnalytics,
        firebaseAuth,
        firestore,
        googlePlayAuth,
        playintegrity,
        playintegrityDebug,
        firebaseRemoteConfig
    )

    override fun platformImplementations() = listOf(firebaseBom)
}