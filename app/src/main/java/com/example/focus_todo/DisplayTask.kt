package com.example.focus_todo



import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DisplayTask : AppCompatActivity() {


    private lateinit var taskList: MutableList<String>
    private lateinit var adapter: ArrayAdapter<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_task)



        // Retrieve the task list from Intent
        taskList = intent.getStringArrayListExtra("TASK_LIST")?.toMutableList() ?: mutableListOf()

        // Set up ListView to display tasks
        val taskListView: ListView = findViewById(R.id.taskListView)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, taskList)
        taskListView.adapter = adapter

        // Handle clicks for edit and delete
        taskListView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            showTaskOptions(position)
        }

        val goToTaskActivityButton: Button = findViewById(R.id.goToTaskActivityButton)
        goToTaskActivityButton.setOnClickListener {
            // Navigate to TaskActivity
            val intent = Intent(this, Taskactivity::class.java)
            startActivity(intent)
        }

        // Set up the "Go to Timer Page" button
        val goToTimerPageButton: Button = findViewById(R.id.goToTimerPageButton)
        goToTimerPageButton.setOnClickListener {
            // Navigate to Timer Page (replace TimerActivity with the actual class name)
            val intent = Intent(this, Timer::class.java)
            startActivity(intent)
        }


    }

    private fun showTaskOptions(position: Int) {
        val options = arrayOf("Edit Task", "Delete Task")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Choose an option")
        builder.setItems(options) { _, which ->
            when (which) {
                0 -> editTask(position)
                1 -> deleteTask(position)
            }
        }
        builder.show()
    }

    private fun editTask(position: Int) {
        val task = taskList[position]

        // Start EditTaskActivity to edit the task
        val intent = Intent(this, EditTaskActivity::class.java).apply {
            putExtra("TASK_INDEX", position)
            putExtra("TASK_DETAILS", task)
        }
        startActivityForResult(intent, 100)
    }

    private fun deleteTask(position: Int) {
        taskList.removeAt(position)
        adapter.notifyDataSetChanged()
        saveTasks()
        Toast.makeText(this, "Task deleted", Toast.LENGTH_SHORT).show()
    }

    private fun saveTasks() {
        // Save the updated task list to SharedPreferences or any storage
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            val updatedTask = data?.getStringExtra("UPDATED_TASK")
            val taskIndex = data?.getIntExtra("TASK_INDEX", -1)
            if (updatedTask != null && taskIndex != -1) {
                taskList[taskIndex!!] = updatedTask
                adapter.notifyDataSetChanged()
                saveTasks()  // Save the updated task list
            }
        }
    }
}