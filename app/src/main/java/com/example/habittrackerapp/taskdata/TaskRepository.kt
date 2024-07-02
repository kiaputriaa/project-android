package com.example.habittrackerapp.taskdata

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.habittrackerapp.todo_list_utils.FilterUtils
import com.example.habittrackerapp.todo_list_utils.TasksFilterType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class TaskRepository(private val tasksDao: TaskDao) {

    companion object {
        const val PAGE_SIZE = 30
        const val PLACEHOLDERS = true

        @Volatile
        private var instance: TaskRepository? = null

        fun getInstance(context: Context): TaskRepository {
            return instance ?: synchronized(this) {
                if (instance == null) {
                    val database = TaskDatabase.getInstance(context, CoroutineScope(SupervisorJob()))
                    instance = TaskRepository(database.taskDao())
                }
                return instance as TaskRepository
            }

        }
    }

    //TODO 4 : Use FilterUtils.getFilteredQuery to create filterable query
    //TODO 5 : Build PagedList with configuration
    fun getTasks(filter: TasksFilterType): LiveData<PagedList<Task>> {
        val query = FilterUtils.getFilteredQuery(filter)

        val dataSourceFactory = tasksDao.getTasks(query)

        val config = PagedList.Config.Builder()
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(PLACEHOLDERS)
            .build()

        return LivePagedListBuilder(dataSourceFactory, config).build()
    }

    fun getTaskById(taskId: Int): LiveData<Task> {
        return tasksDao.getTaskById(taskId)
    }

    fun getNearestActiveTask(): Task {
        return tasksDao.getNearestActiveTask(System.currentTimeMillis())
    }

    suspend fun insertTask(newTask: Task): Long{
        return tasksDao.insertTask(newTask)
    }

    suspend fun deleteTask(task: Task) {
        tasksDao.deleteTask(task)
    }

    suspend fun completeTask(task: Task, isCompleted: Boolean) {
        tasksDao.updateCompleted(task.id, isCompleted)
    }
}