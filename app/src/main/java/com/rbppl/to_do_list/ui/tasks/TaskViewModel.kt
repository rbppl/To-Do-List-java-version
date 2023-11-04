package com.rbppl.to_do_list.ui.tasks
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.rbppl.to_do_list.data.Task
import com.rbppl.to_do_list.data.TaskDatabase
import com.rbppl.to_do_list.data.TaskRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    private val repository: TaskRepository
    val allTasks: LiveData<List<Task>>
    init {
        val taskDao = TaskDatabase.getDatabase(application).taskDao()
        repository = TaskRepository(taskDao)
        allTasks = repository.allTasks
    }
    fun insert(task: Task) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(task)
    }
    fun update(task: Task) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(task)
    }
    fun delete(task: Task) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(task)
    }
}
