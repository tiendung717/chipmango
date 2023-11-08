package io.chipmango.base

import android.util.Log
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import timber.log.Timber

internal class CrashReportingTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO) {
            return
        }
        if (priority == Log.ERROR || priority == Log.WARN) {
            val crashlytics = Firebase.crashlytics
            if (t != null) {
                crashlytics.recordException(t)
            } else {
                val category = when (priority) {
                    Log.ERROR -> "E"
                    Log.WARN -> "W"
                    else -> throw IllegalStateException()
                }
                // https://firebase.google.com/docs/crashlytics/upgrade-sdk?platform=android
                // To log a message to a crash report, use the following syntax:
                // crashlytics.log("E/TAG: my message")
                crashlytics.log("$category/$tag: $message")
            }
        }
    }
}