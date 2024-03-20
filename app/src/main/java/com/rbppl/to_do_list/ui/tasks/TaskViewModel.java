package com.rbppl.to_do_list.ui.tasks;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.rbppl.to_do_list.data.Task;
import com.rbppl.to_do_list.data.TaskDao;
import com.rbppl.to_do_list.data.TaskDatabase;
import com.rbppl.to_do_list.data.TaskRepository;
import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    private TaskRepository repository;
    private LiveData<List<Task>> allTasks;

    public TaskViewModel(Application application) {
        super(application);
        TaskDao taskDao = TaskDatabase.getDatabase(application).taskDao();
        repository = new TaskRepository(taskDao);
        allTasks = repository.getAllTasks();
    }

    public LiveData<List<Task>> getAllTasks() {
        return allTasks;
    }

    public void insert(Task task) {
        new InsertTaskAsyncTask(repository).execute(task);
    }

    public void update(Task task) {
        new UpdateTaskAsyncTask(repository).execute(task);
    }

    public void delete(Task task) {
        new DeleteTaskAsyncTask(repository).execute(task);
    }

    private static class InsertTaskAsyncTask extends AsyncTask<Task, Void, Void> {
        private TaskRepository repository;

        InsertTaskAsyncTask(TaskRepository repository) {
            this.repository = repository;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            repository.insert(tasks[0]);
            return null;
        }
    }

    private static class UpdateTaskAsyncTask extends AsyncTask<Task, Void, Void> {
        private TaskRepository repository;

        UpdateTaskAsyncTask(TaskRepository repository) {
            this.repository = repository;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            repository.update(tasks[0]);
            return null;
        }
    }

    private static class DeleteTaskAsyncTask extends AsyncTask<Task, Void, Void> {
        private TaskRepository repository;

        DeleteTaskAsyncTask(TaskRepository repository) {
            this.repository = repository;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            repository.delete(tasks[0]);
            return null;
        }
    }
}
