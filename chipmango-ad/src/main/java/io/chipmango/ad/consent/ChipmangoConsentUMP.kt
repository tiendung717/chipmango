package io.chipmango.ad.consent

import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.MobileAds
import com.google.android.ump.ConsentDebugSettings
import com.google.android.ump.ConsentForm
import com.google.android.ump.ConsentInformation
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.UserMessagingPlatform
import io.chipmango.ad.BuildConfig
import io.chipmango.ad.request.TestDevice
import timber.log.Timber
import java.util.concurrent.atomic.AtomicBoolean

object ChipmangoConsentUMP {
    private lateinit var consentInformation: ConsentInformation
    private var consentForm: ConsentForm? = null
    private var isMobileAdsInitializeCalled = AtomicBoolean(false)


    fun init(activity: Activity, testDevices: List<String>) {
        val debugSettings = ConsentDebugSettings.Builder(activity)
            .setDebugGeography(
                ConsentDebugSettings
                    .DebugGeography
                    .DEBUG_GEOGRAPHY_EEA
            )
            .apply {
                testDevices.forEach { addTestDeviceHashedId(it) }
            }
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
                ChipmangoConsentUMP.consentForm = consentForm
                if (consentInformation.consentStatus == ConsentInformation.ConsentStatus.REQUIRED) {
                    consentForm.show(activity) {
                        Timber.tag("nt.dung").e("Load consent form error: ${it?.message}")
                        loadForm(activity)
                    }
                }
            },
            {
                Timber.tag("nt.dung").e("Load consent form error: ${it.message}")
            }
        )
    }

    private fun initializeMobileAdsSdk(context: Context) {
        if (isMobileAdsInitializeCalled.getAndSet(true)) {
            return
        }

        MobileAds.initialize(context)
    }
}
