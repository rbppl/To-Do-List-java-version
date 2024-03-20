package com.rbppl.to_do_list;

import android.app.Application;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import com.rbppl.to_do_list.data.Task;
import com.rbppl.to_do_list.data.TaskDao;
import com.rbppl.to_do_list.data.TaskRepository;
import com.rbppl.to_do_list.ui.tasks.TaskViewModel;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;

@RunWith(JUnit4.class)
public class TaskViewModelTest {
    private TaskViewModel taskViewModel;

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setup() {
        Application application = mock(Application.class);
        TaskDao taskDao = mock(TaskDao.class);
        TaskRepository repository = new TaskRepository(taskDao);
        taskViewModel = new TaskViewModel(application);
    }

    @Test
    public void insertTask() {
        Task task = new Task(1, "Test Task", "This is a test task", false);
        taskViewModel.insert(task);
    }
}
