package io.chipmango.ad

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

abstract class AdDisplayActivity : AppCompatActivity() {

    private val viewModel by viewModels<AdViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initialize(this, ::isAdEnabled)
    }

    abstract suspend fun isAdEnabled(): Boolean
}