package com.rbppl.to_do_list.data
import androidx.lifecycle.LiveData
import androidx.room.*
@Dao
interface TaskDao {
    @Query("SELECT * FROM task")
    fun getAllTasks(): LiveData<List<Task>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task): Long
    @Update
    suspend fun update(task: Task): Int
    @Delete
    suspend fun delete(task: Task): Int
}
