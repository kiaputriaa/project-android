package com.example.habittrackerapp.taskdata

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery

@Dao
interface TaskDao {
    @RawQuery(observedEntities = [Task::class])
    fun getTasks(query: SupportSQLiteQuery): DataSource.Factory<Int, Task>

    @Query("Select * from tasks where id = :taskId")
    fun getTaskById(taskId: Int): LiveData<Task>

    @Query("Select * from tasks WHERE dueDate>= :currentDate AND completed == false ORDER BY dueDate ASC LIMIT 1")
    fun getNearestActiveTask(currentDate: Long): Task

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg tasks: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("UPDATE tasks set completed = :completed where id = :taskId")
    suspend fun updateCompleted(taskId: Int, completed: Boolean)
}