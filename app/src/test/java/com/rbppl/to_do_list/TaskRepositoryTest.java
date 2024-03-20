package com.rbppl.to_do_list;

import com.rbppl.to_do_list.data.Task;
import com.rbppl.to_do_list.data.TaskDao;
import com.rbppl.to_do_list.data.TaskDatabase;
import com.rbppl.to_do_list.data.TaskRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class TaskRepositoryTest {
    private TaskDao taskDao;
    private TaskRepository taskRepository;

    @Before
    public void setup() {
        TaskDatabase mockDatabase = Mockito.mock(TaskDatabase.class);
        taskDao = Mockito.mock(TaskDao.class);
        when(mockDatabase.taskDao()).thenReturn(taskDao);
        taskRepository = new TaskRepository(taskDao);
    }

    @Test
    public void insertTask() {
        Task task = new Task(1, "Test Task", "This is a test task", false);
        taskRepository.insert(task);
        verify(taskDao).insert(task);
    }
}
