package io.chipmango.ad

import android.app.ActivityManager
import android.content.Context
import android.os.Build
import android.os.Process
import android.webkit.WebView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import io.chipmango.ad.request.TestDevice

object ChipmangoAds {
    fun init(context: Context) {
        val processName = getProcessName(context)
        val packageName = context.packageName

        if (processName != null && processName.equals(packageName, ignoreCase = true)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                WebView.setDataDirectorySuffix("sz_ad_dirs")
            }
        }

        MobileAds.initialize(context) {
            val testDeviceIds = TestDevice.all().map { it.id }
            val configuration = RequestConfiguration.Builder()
                .setTestDeviceIds(testDeviceIds)
                .build()
            MobileAds.setRequestConfiguration(configuration)
        }
    }

    private fun getProcessName(context: Context): String? {
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val processes = manager.runningAppProcesses
        if (processes.isNullOrEmpty()) return null

        for (processInfo in processes) {
            if (processInfo.pid == Process.myPid()) {
                return processInfo.processName
            }
        }
        return null
    }
}
