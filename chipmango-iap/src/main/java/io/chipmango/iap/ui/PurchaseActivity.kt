package io.chipmango.iap.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import io.chipmango.iap.IapConfiguration
import javax.inject.Inject

@AndroidEntryPoint
class PurchaseActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, PurchaseActivity::class.java))
        }

        fun startWithNewTask(context: Context) {
            context.startActivity(Intent(context, PurchaseActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            })
        }
    }

    @Inject
    lateinit var iapConfiguration: IapConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScreenPurchase(premiumFeatures = iapConfiguration.getPremiumFeatureList()) {
                finish()
            }
        }
    }
}