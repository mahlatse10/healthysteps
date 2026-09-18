package com.example.healthysteps

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class HabitReminderReceiver : BroadcastReceiver() {

    override fun onReceive(
        context: Context,
        intent: Intent
    ) {
        val habitName =
            intent.getStringExtra("habit_name")
                ?: "your habit"

        val channelId = "habit_reminders"

        val notificationManager =
            context.getSystemService(
                Context.NOTIFICATION_SERVICE
            ) as NotificationManager

        val channel = NotificationChannel(
            channelId,
            "Habit Reminders",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Daily HealthySteps habit reminders"
        }

        notificationManager.createNotificationChannel(channel)

        if (
            android.os.Build.VERSION.SDK_INT >= 33 &&
            context.checkSelfPermission(
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        val notification = NotificationCompat.Builder(
            context,
            channelId
        )
            .setSmallIcon(android.R.drawable.ic_popup_reminder)
            .setContentTitle("HealthySteps Reminder")
            .setContentText(
                "Time to complete your habit: $habitName"
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(context).notify(
            habitName.hashCode(),
            notification
        )
    }
}