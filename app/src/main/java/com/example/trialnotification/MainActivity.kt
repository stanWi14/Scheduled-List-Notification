package com.example.trialnotification

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.NotificationCompat
import java.util.*


class MainActivity : AppCompatActivity() {

    private val CHANNEL_ID = "channel_id"
    private val ALARM_REQUEST_CODE_BASE = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnAdd: Button = findViewById(R.id.btnAdd)
        btnAdd.setOnClickListener {
            val notificationTimes = listOf("14:00", "15:00", "16:00")
            scheduleMultipleNotifications(notificationTimes)
        }
    }

    private fun scheduleMultipleNotifications(times: List<String>) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val baseRequestCode = ALARM_REQUEST_CODE_BASE

        for ((index, time) in times.withIndex()) {
            val requestCode = baseRequestCode + index

            val calendar = Calendar.getInstance()
            val (hour, minute) = time.split(":").map { it.toInt() }
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
            calendar.set(Calendar.SECOND, 0)

            val intent = Intent(this, NotificationReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                this,
                requestCode,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent
                )
            } else {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
            }
        }
    }
}