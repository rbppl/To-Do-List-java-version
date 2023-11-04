package com.rbppl.to_do_list
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.rbppl.to_do_list.data.Task
import com.rbppl.to_do_list.data.TaskDao
import com.rbppl.to_do_list.data.TaskDatabase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.IOException
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
@RunWith(JUnit4::class)
class TaskDaoTest {
    private lateinit var database: TaskDatabase
    private lateinit var taskDao: TaskDao
    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, TaskDatabase::class.java).build()
        taskDao = database.taskDao()
    }
    @After
    @Throws(IOException::class)
    fun closeDatabase() {
        database.close()
    }
    @Test
    fun insertAndGetTask() = runBlocking {
        val task = Task(id = 1, title = "Test Task", description = "This is a test task", isCompleted = false)
        taskDao.insert(task)
        val loaded = taskDao.getAllTasks().waitForValue()?.firstOrNull()
        assertNotNull(loaded)
        assertEquals(task.id, loaded?.id)
        assertEquals(task.title, loaded?.title)
        assertEquals(task.description, loaded?.description)
        assertEquals(task.isCompleted, loaded?.isCompleted)
    }
}
fun <T> LiveData<T>.waitForValue(): T? {
    var data: T? = null
    val latch = CountDownLatch(1)
    observeForever { t ->
        data = t
        latch.countDown()
    }
    latch.await(2, TimeUnit.SECONDS)
    return data
}
