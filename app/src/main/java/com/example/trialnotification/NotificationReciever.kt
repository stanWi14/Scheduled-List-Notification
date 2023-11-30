package com.example.trialnotification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        showNotification(context)
    }

    private fun showNotification(context: Context?) {
        val notificationManager = NotificationManagerCompat.from(context!!)
        val notification = NotificationCompat.Builder(context, "channel_id")
            .setContentTitle("Scheduled Notification")
            .setContentText("This notification was scheduled at a specific time.")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .build()

        notificationManager.notify(1, notification)
    }
}