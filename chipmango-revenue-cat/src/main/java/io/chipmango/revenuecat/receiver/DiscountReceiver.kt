package io.chipmango.revenuecat.receiver

import android.Manifest
import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
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
            val channelId = "discountOffer"
            createNotificationChannelIfNotExists(context, channelId)
            notificationManager.notify(Random.nextInt(), createNotification(context, channelId, title, message))
        }
    }

    private fun hasPostNotificationPermission(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

    private fun createNotificationChannelIfNotExists(context: Context, channelId: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val notificationManager = NotificationManagerCompat.from(context)
            if (notificationManager.getNotificationChannel(channelId) == null) {
                val name = "Discount Offer Channel"
                val descriptionText = "Channel for show discount offer"
                val importance = NotificationManager.IMPORTANCE_HIGH
                val channel = NotificationChannel(channelId, name, importance).apply {
                    description = descriptionText
                }
                notificationManager.createNotificationChannel(channel)
            }
        }
    }

    abstract fun createNotification(context: Context, channelId: String, title: String?, content: String?): Notification

}