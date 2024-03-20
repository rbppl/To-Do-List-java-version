package com.rbppl.to_do_list.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM task")
    public LiveData<List<Task>> getAllTasks();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long insert(Task task);

    @Update
    public int update(Task task);

    @Delete
    public int delete(Task task);
}
