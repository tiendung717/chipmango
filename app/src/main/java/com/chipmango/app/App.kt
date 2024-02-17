package com.chipmango.app

import dagger.hilt.android.HiltAndroidApp
import io.chipmango.base.CoreApplication
import io.chipmango.revenuecat.RevenueCat
import javax.inject.Inject

@HiltAndroidApp
class App : CoreApplication() {


    @Inject
    lateinit var revenueCat: RevenueCat

    override fun isLogEnabled(): Boolean {
        return true
    }

    override fun onCreate() {
        super.onCreate()
        revenueCat.init(this, "goog_pyAPqDZUVyHHDXMlcLPUvNWgPQo", true)
    }
}