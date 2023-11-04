package com.rbppl.to_do_list
import com.rbppl.to_do_list.data.Task
import com.rbppl.to_do_list.data.TaskDao
import com.rbppl.to_do_list.data.TaskDatabase
import com.rbppl.to_do_list.data.TaskRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
@RunWith(JUnit4::class)
class TaskRepositoryTest {
    private lateinit var taskDao: TaskDao
    private lateinit var taskRepository: TaskRepository
    @Before
    fun setup() {
        val mockDatabase = mock(TaskDatabase::class.java)
        taskDao = mock(TaskDao::class.java)
        `when`(mockDatabase.taskDao()).thenReturn(taskDao)
        taskRepository = TaskRepository(taskDao)
    }
    @Test
    fun insertTask() = runBlocking {
        val task = Task(id = 1, title = "Test Task", description = "This is a test task", isCompleted = false)
        taskRepository.insert(task)
        verify(taskDao).insert(task)
    }
}
