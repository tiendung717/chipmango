package io.chipmango.ad

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.os.Build
import android.os.Process
import android.webkit.WebView
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.android.gms.ads.initialization.AdapterStatus
import com.google.android.ump.ConsentDebugSettings
import com.google.android.ump.ConsentForm
import com.google.android.ump.ConsentInformation
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.UserMessagingPlatform
import io.chipmango.ad.di.AdTestDeviceList
import io.chipmango.ad.repo.ChipmangoAdRepo
import timber.log.Timber
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChipmangoAds @Inject constructor(
    private val adRepo: ChipmangoAdRepo
) {

    private lateinit var consentInformation: ConsentInformation
    private var consentForm: ConsentForm? = null
    private var isMobileAdsInitializeCalled = AtomicBoolean(false)

    fun init(activity: Activity) {
        val debugSettings = ConsentDebugSettings.Builder(activity)
            .setDebugGeography(
                ConsentDebugSettings
                    .DebugGeography
                    .DEBUG_GEOGRAPHY_EEA
            )
            .build()

        val params = ConsentRequestParameters.Builder()
            .setTagForUnderAgeOfConsent(false)
            .apply {
                if (BuildConfig.DEBUG) {
                    setConsentDebugSettings(debugSettings)
                }
            }
            .build()

        consentInformation = UserMessagingPlatform.getConsentInformation(activity)
        consentInformation.requestConsentInfoUpdate(
            activity,
            params,
            {
                if (consentInformation.isConsentFormAvailable) {
                    loadForm(activity)
                }

                // Consent has been gathered.
                if (consentInformation.canRequestAds()) {
                    initializeMobileAdsSdk(activity);
                }
            },
            { error ->
                Timber.tag("nt.dung").e("getConsentInformation error: ${error.message}")
            }
        )

        // Check if you can initialize the Google Mobile Ads SDK in parallel
        // while checking for new consent information. Consent obtained in
        // the previous session can be used to request ads.
        if (consentInformation.canRequestAds()) {
            initializeMobileAdsSdk(activity)
        }

    }

    private fun loadForm(activity: Activity) {
        UserMessagingPlatform.loadConsentForm(
            activity,
            { consentForm ->
                this.consentForm = consentForm
                if (consentInformation.consentStatus == ConsentInformation.ConsentStatus.REQUIRED) {
                    consentForm.show(activity) {
                        if (it == null) {
                            initializeMobileAdsSdk(activity)
                        }
                    }
                }
            },
            {
                Timber.tag("nt.dung").e("Load consent form error: ${it.message}")
            }
        )
    }

    private fun initializeMobileAdsSdk(context: Context) {
        if (isMobileAdsInitializeCalled.get()) {
            return
        }

        MobileAds.initialize(context) {
            val configuration = RequestConfiguration.Builder().build()
            MobileAds.setRequestConfiguration(configuration)
            isMobileAdsInitializeCalled.set(true)
            adRepo.setAdInitialized(
                it.adapterStatusMap.any { entry ->
                    entry.value.initializationState == AdapterStatus.State.READY
                }
            )
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
