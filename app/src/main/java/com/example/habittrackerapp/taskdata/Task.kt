package com.example.habittrackerapp.taskdata

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String,

    val description: String,

    @ColumnInfo(name = "dueDate")
    val dueDateMillis: Long,

    @ColumnInfo(name = "completed")
    val isCompleted: Boolean = false
)
