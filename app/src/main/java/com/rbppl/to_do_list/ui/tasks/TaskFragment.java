package com.rbppl.to_do_list.ui.tasks;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.rbppl.to_do_list.R;
import com.rbppl.to_do_list.data.Task;
import com.rbppl.to_do_list.data.TaskEventListener;
import com.rbppl.to_do_list.databinding.FragmentTaskBinding;

public class TaskFragment extends Fragment implements TaskEventListener {
    private FragmentTaskBinding binding;
    private TaskListAdapter taskAdapter;
    private TaskViewModel taskViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTaskBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        taskAdapter = new TaskListAdapter();
        binding.taskRecyclerView.setAdapter(taskAdapter);

        binding.fabAddTask.setOnClickListener(v -> showAddTaskDialog());

        taskViewModel = new ViewModelProvider(this, new TaskViewModelFactory(requireActivity().getApplication()))
                .get(TaskViewModel.class);

        taskViewModel.getAllTasks().observe(getViewLifecycleOwner(), tasks -> taskAdapter.submitList(tasks));

        taskAdapter.setTaskEventListener(this);

        return root;
    }

    private void showAddTaskDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(requireContext());
        alertDialog.setTitle(R.string.add_a_task);
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_task, null);
        EditText taskTitleInput = dialogView.findViewById(R.id.taskTitleInput);
        EditText taskDescriptionInput = dialogView.findViewById(R.id.taskDescriptionInput);
        alertDialog.setView(dialogView);
        alertDialog.setPositiveButton(R.string.add, (dialog, which) -> {
            String taskTitle = taskTitleInput.getText().toString();
            String taskDescription = taskDescriptionInput.getText().toString();
            if (!taskTitle.trim().isEmpty()) {
                Task newTask = new Task(0, taskTitle, taskDescription, false);
                taskViewModel.insert(newTask);
            }
        });
        alertDialog.setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }

    @Override
    public void onDeleteTask(Task task) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(binding.getRoot().getContext());
        alertDialog.setTitle(R.string.delete_task);
        alertDialog.setMessage(R.string.delete_qustion);
        alertDialog.setPositiveButton(R.string.yes, (dialog, which) -> taskViewModel.delete(task));
        alertDialog.setNegativeButton(R.string.no, (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }

    @Override
    public void onEditTask(Task task) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(binding.getRoot().getContext());
        alertDialog.setTitle(R.string.edit_task);
        View dialogView = LayoutInflater.from(binding.getRoot().getContext()).inflate(R.layout.dialog_edit_task, null);
        EditText taskTitleInput = dialogView.findViewById(R.id.taskTitleInput);
        EditText taskDescriptionInput = dialogView.findViewById(R.id.taskDescriptionInput);
        taskTitleInput.setText(task.getTitle());
        taskDescriptionInput.setText(task.getDescription());
        alertDialog.setView(dialogView);
        alertDialog.setPositiveButton(R.string.save, (dialog, which) -> {
            String newTitle = taskTitleInput.getText().toString();
            String newDescription = taskDescriptionInput.getText().toString();
            if (!newTitle.trim().isEmpty()) {
                Task updatedTask = new Task(task.getId(), newTitle, newDescription, task.isCompleted());
                taskViewModel.update(updatedTask);
            }
        });
        alertDialog.setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }

    @Override
    public void onTaskStatusChanged(Task task) {
        taskViewModel.update(task);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
