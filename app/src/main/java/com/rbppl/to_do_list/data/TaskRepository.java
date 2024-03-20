package com.rbppl.to_do_list.data;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TaskRepository {
    private final TaskDao taskDao;
    public LiveData<List<Task>> allTasks;

    public TaskRepository(TaskDao taskDao) {
        this.taskDao = taskDao;
        allTasks = taskDao.getAllTasks();
    }
    public LiveData<List<Task>> getAllTasks() {
        return taskDao.getAllTasks();
    }
    public void insert(Task task) {
        taskDao.insert(task);
    }

    public void update(Task task) {
        taskDao.update(task);
    }

    public void delete(Task task) {
        taskDao.delete(task);
    }
}
