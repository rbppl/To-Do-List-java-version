package com.rbppl.to_do_list;

import android.content.Context;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import com.rbppl.to_do_list.data.Task;
import com.rbppl.to_do_list.data.TaskDao;
import com.rbppl.to_do_list.data.TaskDatabase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@RunWith(JUnit4.class)
public class TaskDaoTest {
    private TaskDatabase database;
    private TaskDao taskDao;

    @Before
    public void setup() {
        Context context = ApplicationProvider.getApplicationContext();
        database = Room.inMemoryDatabaseBuilder(context, TaskDatabase.class).build();
        taskDao = database.taskDao();
    }

    @After
    public void closeDatabase() throws IOException {
        database.close();
    }

    @Test
    public void insertAndGetTask() throws InterruptedException {
        Task task = new Task(1, "Test Task", "This is a test task", false);
        new InsertTaskAsyncTask(taskDao).execute(task);

        LiveData<List<Task>> allTasks = taskDao.getAllTasks();
        Task loaded = (Task) getValue(allTasks);

        assert loaded != null;
        assert task.getId() == loaded.getId();
        assert task.getTitle().equals(loaded.getTitle());
        assert task.getDescription().equals(loaded.getDescription());
        assert task.isCompleted() == loaded.isCompleted();
    }

    private static class InsertTaskAsyncTask extends AsyncTask<Task, Void, Void> {
        private final TaskDao taskDao;

        InsertTaskAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.insert(tasks[0]);
            return null;
        }
    }

    private static <T> T getValue(final LiveData<T> liveData) throws InterruptedException {
        final Object[] data = new Object[1];
        final CountDownLatch latch = new CountDownLatch(1);

        liveData.observeForever(value -> {
            data[0] = value;
            latch.countDown();
        });

        latch.await(2, TimeUnit.SECONDS);
        //noinspection unchecked
        return (T) data[0];
    }
}
