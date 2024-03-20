package com.rbppl.to_do_list.ui.tasks;

import androidx.recyclerview.widget.DiffUtil;

import com.rbppl.to_do_list.data.Task;

import java.util.Objects; // Import for object comparison

public class TaskDiffCallback extends DiffUtil.ItemCallback<Task> {

    @Override
    public boolean areItemsTheSame(Task oldItem, Task newItem) {
        return oldItem != null && newItem != null && oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(Task oldItem, Task newItem) {
        return Objects.equals(oldItem.getTitle(), newItem.getTitle()) &&
                Objects.equals(oldItem.getDescription(), newItem.getDescription()) &&
                oldItem.isCompleted() == newItem.isCompleted();
    }
}
