package com.chipmango.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.chipmango.ad.ChipmangoAds
import io.chipmango.base.CoreApplication
import timber.log.Timber

@HiltAndroidApp
class App : CoreApplication() {
    override fun isLogEnabled(): Boolean {
        return true
    }

    override fun onCreate() {
        super.onCreate()
        ChipmangoAds.init(this)
    }
}