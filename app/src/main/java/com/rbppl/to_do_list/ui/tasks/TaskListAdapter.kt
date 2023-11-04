package com.rbppl.to_do_list.ui.tasks
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rbppl.to_do_list.data.Task
import com.rbppl.to_do_list.data.TaskEventListener
import com.rbppl.to_do_list.databinding.TaskListItemBinding
class TaskListAdapter : ListAdapter<Task, TaskListAdapter.TaskViewHolder>(TaskDiffCallback()) {
    private var taskEventListener: TaskEventListener? = null
    fun setTaskEventListener(listener: TaskEventListener) {
        taskEventListener = listener
    }
    private var taskEditClickListener: ((Task) -> Unit)? = null
    private var taskDeleteClickListener: ((Task) -> Unit)? = null
    inner class TaskViewHolder(private val binding: TaskListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var taskTitle = binding.textTitle
        var taskDescription = binding.textDescription
        var taskCheckBox = binding.checkboxIsCompleted
        init {
            binding.imageButtonEdit.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val task = getItem(position)
                    taskEditClickListener?.invoke(task)
                    taskEventListener?.onEditTask(task)
                }
            }
            binding.imageButtonDelete.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val task = getItem(position)
                    taskDeleteClickListener?.invoke(task)
                    taskEventListener?.onDeleteTask(task)
                }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TaskListItemBinding.inflate(inflater, parent, false)
        return TaskViewHolder(binding)
    }
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = getItem(position)
                holder.taskTitle.text = task.title
                holder.taskDescription.text = task.description
        holder.taskCheckBox.isChecked = task.isCompleted
        holder.taskCheckBox.setOnCheckedChangeListener { _, isChecked ->
            task.isCompleted = isChecked
            taskEventListener?.onTaskStatusChanged(task)
    }
}}
