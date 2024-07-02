package com.example.habittrackerapp.ui.add

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.habittrackerapp.R
import com.example.habittrackerapp.todo_list_utils.DatePickerFragment
import com.example.habittrackerapp.vmfactory.TaskViewModelFactory
import com.google.android.material.textfield.TextInputEditText
import com.example.habittrackerapp.taskdata.Task
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddTaskActivity : AppCompatActivity(), DatePickerFragment.DialogDateListener {
    private var dueDateMillis: Long = System.currentTimeMillis()

    private lateinit var addTaskViewModel: AddTaskViewModel
    private lateinit var edTitle: TextInputEditText
    private lateinit var edDescription: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)
        init()
        supportActionBar?.title = getString(R.string.add_task)

        val factory = TaskViewModelFactory.getInstance(this)
        addTaskViewModel = ViewModelProvider(this, factory).get(AddTaskViewModel::class.java)

    }

    private fun init(){
        edTitle = findViewById(R.id.add_ed_title)
        edDescription = findViewById(R.id.add_ed_description)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save -> {
                //TODO 12 : Create AddTaskViewModel and insert new task to database
                val newTask = Task(
                    title = edTitle.text.toString(),
                    description = edDescription.text.toString(),
                    dueDateMillis = dueDateMillis
                )
                addTaskViewModel.addTask(newTask)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun showDatePicker(view: View) {
        val dialogFragment = DatePickerFragment()
        dialogFragment.show(supportFragmentManager, "datePicker")
    }

    override fun onDialogDateSet(tag: String?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        findViewById<TextView>(R.id.add_tv_due_date).text = dateFormat.format(calendar.time)

        dueDateMillis = calendar.timeInMillis
    }
}