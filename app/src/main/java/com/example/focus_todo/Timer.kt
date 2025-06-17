package com.example.focus_todo

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Timer : AppCompatActivity() {

    private lateinit var tvTimer: TextView
    private lateinit var btnStart: Button
    private lateinit var btnStop: Button
    private lateinit var btnReset: Button

    private var startTime = 0L
    private var timeInMilliseconds = 0L
    private var timeSwapBuff = 0L
    private var updatedTime = 0L

    private val handler = Handler()
    private var isRunning = false

    private val runnable: Runnable = object : Runnable {
        override fun run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime
            updatedTime = timeSwapBuff + timeInMilliseconds

            val secs = (updatedTime / 1000).toInt()
            val mins = secs / 60
            val hrs = mins / 60
            val seconds = secs % 60
            val milliseconds = (updatedTime % 1000).toInt()

            tvTimer.text = String.format("%02d:%02d:%02d", hrs, mins, seconds)

            handler.postDelayed(this, 0)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)


        val timer: Button = findViewById(R.id.goback)
        timer.setOnClickListener {
            // Navigate to TaskActivity
            val intent3 = Intent(this, DisplayTask::class.java)
            startActivity(intent3)
        }

        tvTimer = findViewById(R.id.tv_timer)
        btnStart = findViewById(R.id.btn_start)
        btnStop = findViewById(R.id.btn_stop)
        btnReset = findViewById(R.id.btn_reset)

        btnStart.setOnClickListener {
            if (!isRunning) {
                startTime = SystemClock.uptimeMillis()
                handler.postDelayed(runnable, 0)
                isRunning = true
            }
        }

        btnStop.setOnClickListener {
            if (isRunning) {
                timeSwapBuff += timeInMilliseconds
                handler.removeCallbacks(runnable)
                isRunning = false
            }
        }

        btnReset.setOnClickListener {
            startTime = 0L
            timeSwapBuff = 0L
            timeInMilliseconds = 0L
            updatedTime = 0L
            tvTimer.text = "00:00:00"
            handler.removeCallbacks(runnable)
            isRunning = false
        }

    }
}