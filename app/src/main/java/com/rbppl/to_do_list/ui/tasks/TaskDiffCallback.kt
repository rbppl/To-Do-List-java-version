package com.rbppl.to_do_list.ui.tasks
import androidx.recyclerview.widget.DiffUtil
import com.rbppl.to_do_list.data.Task
class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.id == newItem.id }
    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem }
}
