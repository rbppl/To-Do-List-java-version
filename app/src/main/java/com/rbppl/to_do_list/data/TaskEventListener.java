package com.rbppl.to_do_list.data;

public interface TaskEventListener {
    void onDeleteTask(Task task);
    void onEditTask(Task task);
    void onTaskStatusChanged(Task task);
}
