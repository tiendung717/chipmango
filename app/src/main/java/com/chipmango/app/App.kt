package com.chipmango.app

import dagger.hilt.android.HiltAndroidApp
import io.chipmango.base.CoreApplication

@HiltAndroidApp
class App : CoreApplication() {
    override fun isLogEnabled(): Boolean {
        return true
    }

    override fun onCreate() {
        super.onCreate()
    }
}