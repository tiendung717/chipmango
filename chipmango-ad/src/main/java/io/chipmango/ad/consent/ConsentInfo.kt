package io.chipmango.ad.consent

import android.app.Activity
import com.google.android.ump.ConsentDebugSettings
import com.google.android.ump.ConsentForm
import com.google.android.ump.ConsentInformation
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.UserMessagingPlatform
import io.chipmango.ad.BuildConfig
import io.chipmango.ad.request.TestDevice
import timber.log.Timber

object ConsentInfo {
    private lateinit var consentInformation: ConsentInformation
    private var consentForm: ConsentForm? = null

    fun init(activity: Activity) {
        val debugSettings = ConsentDebugSettings.Builder(activity)
            .setDebugGeography(
                ConsentDebugSettings
                    .DebugGeography
                    .DEBUG_GEOGRAPHY_EEA
            )
            .addTestDeviceHashedId(TestDevice.Pixel3.id)
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
            },
            { error ->
                Timber.tag("nt.dung").e("getConsentInformation error: ${error.message}")
            }
        )
    }

    private fun loadForm(activity: Activity) {
        UserMessagingPlatform.loadConsentForm(
            activity,
            { consentForm ->
                ConsentInfo.consentForm = consentForm
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
}
