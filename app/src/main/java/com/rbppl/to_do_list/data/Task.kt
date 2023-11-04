package com.rbppl.to_do_list.data
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "task")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val description: String,
    var isCompleted: Boolean
)
