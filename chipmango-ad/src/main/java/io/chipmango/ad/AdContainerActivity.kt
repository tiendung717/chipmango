package io.chipmango.ad

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

abstract class AdContainerActivity : AppCompatActivity() {

    @Inject
    lateinit var chipmangoAds: ChipmangoAds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isAdEnabled()) {
            chipmangoAds.init(this)
        }
    }

    abstract fun isAdEnabled(): Boolean
}