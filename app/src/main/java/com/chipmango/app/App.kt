package com.chipmango.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.chipmango.ad.ChipmangoAds
import timber.log.Timber

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        ChipmangoAds.init(this)
    }
}