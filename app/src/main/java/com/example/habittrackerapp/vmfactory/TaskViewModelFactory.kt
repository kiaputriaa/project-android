package com.example.habittrackerapp.vmfactory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.habittrackerapp.taskdata.TaskRepository
import com.example.habittrackerapp.ui.add.AddTaskViewModel
import com.example.habittrackerapp.ui.detail.DetailTaskViewModel
import com.example.habittrackerapp.ui.list.TaskViewModel

class TaskViewModelFactory private constructor(private val taskRepository: TaskRepository) :
    ViewModelProvider.Factory{

    companion object {
        @Volatile
        private var instance: TaskViewModelFactory? = null

        fun getInstance(context: Context): TaskViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: TaskViewModelFactory(
                    TaskRepository.getInstance(context)
                )
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(TaskViewModel::class.java) -> {
                TaskViewModel(taskRepository) as T
            }
            modelClass.isAssignableFrom(DetailTaskViewModel::class.java) -> {
                DetailTaskViewModel(taskRepository) as T
            }
            modelClass.isAssignableFrom(AddTaskViewModel::class.java) -> {
                AddTaskViewModel(taskRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}