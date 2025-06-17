package com.example.focus_todo


import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Reminder : AppCompatActivity() {

    private lateinit var etTime: EditText
    private lateinit var btnSetReminder: Button
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_reminder)

        etTime = findViewById(R.id.et_time)
        btnSetReminder = findViewById(R.id.btn_set_reminder)

        btnSetReminder.setOnClickListener {
            val timeInSeconds = etTime.text.toString().toIntOrNull()
            if (timeInSeconds != null) {
                setReminder(timeInSeconds)
            }
        }

    }

    private fun setReminder(timeInSeconds: Int) {

        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, ReminderReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val triggerTime = SystemClock.elapsedRealtime() + timeInSeconds * 1000
        alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, pendingIntent)

    }
}