package com.rbppl.to_do_list.data
interface TaskEventListener {
    fun onDeleteTask(task: Task)
    fun onEditTask(task: Task)
    fun onTaskStatusChanged(task: Task)
}
