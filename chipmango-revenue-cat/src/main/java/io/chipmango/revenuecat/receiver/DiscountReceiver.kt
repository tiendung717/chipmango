package io.chipmango.revenuecat.receiver

import android.Manifest
import android.annotation.SuppressLint
import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import kotlin.random.Random

abstract class DiscountReceiver : BroadcastReceiver() {
    @SuppressLint("MissingPermission")
    override fun onReceive(context: Context, intent: Intent) {
        val message = intent.getStringExtra("message")
        val title = intent.getStringExtra("title")

        val notificationManager = NotificationManagerCompat.from(context)
        if (hasPostNotificationPermission(context)) {
            notificationManager.notify(Random.nextInt(), createNotification(title, message))
        }
    }

    private fun hasPostNotificationPermission(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

    abstract fun createNotification(title: String?, content: String?): Notification

}