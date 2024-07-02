package com.example.habittrackerapp.ui.detail

import android.os.Bundle
import android.text.Editable
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.habittrackerapp.R
import com.example.habittrackerapp.taskdata.Task
import com.example.habittrackerapp.todo_list_utils.DateConverter
import com.example.habittrackerapp.todo_list_utils.TASK_ID
import com.example.habittrackerapp.vmfactory.TaskViewModelFactory
import com.google.android.material.textfield.TextInputEditText

class DetailTaskActivity : AppCompatActivity() {
    private lateinit var detailTaskViewModel: DetailTaskViewModel
    private lateinit var edTitle: TextInputEditText
    private lateinit var edDescription: TextInputEditText
    private lateinit var edDueDate: TextInputEditText
    private lateinit var btnDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_task)

        //TODO 11 : Show detail task and implement delete action
        val factory = TaskViewModelFactory.getInstance(this)
        detailTaskViewModel = ViewModelProvider(this, factory).get(DetailTaskViewModel::class.java)

        init()
        val taskId = intent.getIntExtra(TASK_ID, -1)
        detailTaskViewModel.apply {
            setTaskId(taskId)
            task.observe(this@DetailTaskActivity){ task ->
                task?.let {
                    setDetailContent(it)
                }?: run {
                    finish()
                }
            }
        }

        btnDelete.setOnClickListener(){
            detailTaskViewModel.deleteTask()
        }
    }

    private fun init(){
        edTitle = findViewById(R.id.detail_ed_title)
        edDescription = findViewById(R.id.detail_ed_description)
        edDueDate = findViewById(R.id.detail_ed_due_date)
        btnDelete = findViewById(R.id.btn_delete_task)
    }

    private fun setDetailContent(task : Task){
        edTitle.text = newEditable(task.title)
        edDescription.text = newEditable(task.description)
        edDueDate.text = newEditable(DateConverter.convertMillisToString(task.dueDateMillis))
    }

    private fun newEditable(string: String) = Editable.Factory.getInstance().newEditable(string)
}