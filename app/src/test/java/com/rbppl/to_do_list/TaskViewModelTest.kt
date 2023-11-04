package com.rbppl.to_do_list
import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rbppl.to_do_list.data.Task
import com.rbppl.to_do_list.data.TaskDao
import com.rbppl.to_do_list.data.TaskRepository
import com.rbppl.to_do_list.ui.tasks.TaskViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.mock
@RunWith(JUnit4::class)
class TaskViewModelTest {
    private lateinit var taskViewModel: TaskViewModel
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    @Before
    fun setup() {
        val application = mock(Application::class.java)
        val taskDao = mock(TaskDao::class.java)
        val repository = TaskRepository(taskDao)
        taskViewModel = TaskViewModel(application)
    }
    @Test
    fun insertTask() = runBlocking {
        val task = Task(id = 1, title = "Test Task", description = "This is a test task", isCompleted = false)
        taskViewModel.insert(task)
    }
}
