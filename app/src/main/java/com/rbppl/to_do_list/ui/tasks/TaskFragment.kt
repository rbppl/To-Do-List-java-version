package com.rbppl.to_do_list.ui.tasks
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.rbppl.to_do_list.R
import com.rbppl.to_do_list.data.Task
import com.rbppl.to_do_list.data.TaskEventListener
import com.rbppl.to_do_list.databinding.FragmentTaskBinding
class TaskFragment : Fragment() , TaskEventListener {
    private var _binding: FragmentTaskBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        taskViewModel = ViewModelProvider(this, TaskViewModelFactory(requireActivity().application))
            .get(TaskViewModel::class.java)
    }
    private val taskAdapter = TaskListAdapter()
    private lateinit var taskViewModel: TaskViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.taskRecyclerView.adapter = taskAdapter
        binding.fabAddTask.setOnClickListener {
            showAddTaskDialog()
        }
        taskViewModel = ViewModelProvider(this, TaskViewModelFactory(requireActivity().application))
            .get(TaskViewModel::class.java)
        taskViewModel.allTasks.observe(viewLifecycleOwner, { tasks ->
            taskAdapter.submitList(tasks)
        })
        taskAdapter.setTaskEventListener(this)
        return root
    }
    private fun showAddTaskDialog() {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Добавить задачу")
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_task, null)
        val taskTitleInput = dialogView.findViewById<EditText>(R.id.taskTitleInput)
        val taskDescriptionInput = dialogView.findViewById<EditText>(R.id.taskDescriptionInput)
        alertDialog.setView(dialogView)
        alertDialog.setPositiveButton("Добавить") { _, _ ->
            val taskTitle = taskTitleInput.text.toString()
            val taskDescription = taskDescriptionInput.text.toString()
            if (taskTitle.isNotBlank()) {
                val newTask = Task(id = 0, title = taskTitle, description = taskDescription, isCompleted = false)
                taskViewModel.insert(newTask)
            } else {
            }
        }
        alertDialog.setNegativeButton("Отмена") { dialog, _ ->
            dialog.dismiss()
        }
        alertDialog.show()
    }
    override fun onDeleteTask(task: Task) {
        val alertDialog = AlertDialog.Builder(binding.root.context)
        alertDialog.setTitle("Удалить задачу")
        alertDialog.setMessage("Вы уверены, что хотите удалить эту задачу?")
        alertDialog.setPositiveButton("Да") { _, _ ->
            taskViewModel.delete(task)
        }
        alertDialog.setNegativeButton("Нет") { dialog, _ ->
            dialog.dismiss()
        }
        alertDialog.show()
    }
    override fun onEditTask(task: Task) {
        val alertDialog = AlertDialog.Builder(binding.root.context)
        alertDialog.setTitle("Edit Tast")
        val dialogView = LayoutInflater.from(binding.root.context).inflate(R.layout.dialog_edit_task, null)
        val taskTitleInput = dialogView.findViewById<EditText>(R.id.taskTitleInput)
        val taskDescriptionInput = dialogView.findViewById<EditText>(R.id.taskDescriptionInput)
        taskTitleInput.setText(task.title)
        taskDescriptionInput.setText(task.description)
        alertDialog.setView(dialogView)
        alertDialog.setPositiveButton("Save") { _, _ ->
            val newTitle = taskTitleInput.text.toString()
            val newDescription = taskDescriptionInput.text.toString()
            if (newTitle.isNotBlank()) {
                val updatedTask = task.copy(title = newTitle, description = newDescription)
                taskViewModel.update(updatedTask)
            } else {
            }
        }
        alertDialog.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        alertDialog.show()
    }
    override fun onTaskStatusChanged(task: Task) {
        taskViewModel.update(task)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
