package io.chipmango.base

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import timber.log.Timber
import javax.inject.Inject

abstract class CoreApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    abstract fun isLogEnabled(): Boolean

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder().setWorkerFactory(workerFactory).build()
    }

    override fun onCreate() {
        super.onCreate()
        setupLog()
    }

    private fun setupLog() {
        if (isLogEnabled()) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashReportingTree())
        }
    }
}

