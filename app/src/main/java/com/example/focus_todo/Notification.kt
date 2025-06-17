package com.example.focus_todo


import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat

object Notification {
    const val CHANNEL_ID = "task_channel_id"
    private const val CHANNEL_NAME = "Task Notifications"
    private const val CHANNEL_DESCRIPTION = "Channel for task notifications"

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH // Make sure notifications pop up
            ).apply {
                description = CHANNEL_DESCRIPTION
                enableLights(true)
                enableVibration(true)
            }
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun sendNotification(context: Context, task: TaskD) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification) // Ensure this drawable exists
            .setContentTitle("New Task Added")
            .setContentText("Task: ${task.title}")
            .setPriority(NotificationCompat.PRIORITY_HIGH) // Set priority to high for popups
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000)) // Vibration pattern
            .setDefaults(NotificationCompat.DEFAULT_ALL) // Ensure sound, vibration, etc.
            .build()

        notificationManager.notify(1, notification)
    }
}