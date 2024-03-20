package com.rbppl.to_do_list.ui.tasks;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.rbppl.to_do_list.data.Task;
import com.rbppl.to_do_list.data.TaskEventListener;
import com.rbppl.to_do_list.databinding.TaskListItemBinding;

public class TaskListAdapter extends ListAdapter<Task, TaskListAdapter.TaskViewHolder> {
    private TaskEventListener taskEventListener;
    private OnClickListener taskEditClickListener;
    private OnClickListener taskDeleteClickListener;

    public TaskListAdapter() {
        super(new TaskDiffCallback());
    }

    public void setTaskEventListener(TaskEventListener listener) {
        taskEventListener = listener;
    }

    public void setTaskEditClickListener(OnClickListener listener) {
        taskEditClickListener = listener;
    }

    public void setTaskDeleteClickListener(OnClickListener listener) {
        taskDeleteClickListener = listener;
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {
        TaskListItemBinding binding;

        TaskViewHolder(TaskListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.imageButtonEdit.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Task task = getItem(position);
                    if (taskEditClickListener != null) {
                        taskEditClickListener.onClick(task);
                    } else if (taskEventListener != null) {
                        taskEventListener.onEditTask(task);
                    }
                }
            });

            binding.imageButtonDelete.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Task task = getItem(position);
                    if (taskDeleteClickListener != null) {
                        taskDeleteClickListener.onClick(task);
                    } else if (taskEventListener != null) {
                        taskEventListener.onDeleteTask(task);
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        TaskListItemBinding binding = TaskListItemBinding.inflate(inflater, parent, false);
        return new TaskViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = getItem(position);
        holder.binding.textTitle.setText(task.getTitle());
        holder.binding.textDescription.setText(task.getDescription());
        holder.binding.checkboxIsCompleted.setChecked(task.isCompleted());

        holder.binding.checkboxIsCompleted.setOnCheckedChangeListener((buttonView, isChecked) -> {
            task.setCompleted(isChecked);
            if (taskEventListener != null) {
                taskEventListener.onTaskStatusChanged(task);
            }
        });
    }

    interface OnClickListener {
        void onClick(Task task);
    }
}
