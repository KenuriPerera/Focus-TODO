package com.example.focus_todo



import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class EditTaskActivity : AppCompatActivity() {

    private lateinit var taskDetails: String
    private var taskIndex: Int = -1

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_task)

        // Retrieve task details from Intent
        taskDetails = intent.getStringExtra("TASK_DETAILS") ?: ""
        taskIndex = intent.getIntExtra("TASK_INDEX", -1)

        // Set the task details in the EditText
        val taskEditText: EditText = findViewById(R.id.editTask)
        taskEditText.setText(taskDetails)

        val saveButton: Button = findViewById(R.id.saveTaskButton)
        saveButton.setOnClickListener {
            val updatedTask = taskEditText.text.toString()
            val resultIntent = intent.apply {
                putExtra("UPDATED_TASK", updatedTask)
                putExtra("TASK_INDEX", taskIndex)
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()  // Return to DisplayTaskActivity
        }
    }
}
