package com.example.view.todo.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.view.todo.R;
import com.example.view.todo.model.Task;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> allTasks;

    public TaskAdapter(List<Task> allTasks) {
        this.allTasks = allTasks;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.task_row, parent, false);

        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {

        Task currentTask = allTasks.get(position);

        holder.textViewTask.setText(currentTask.getText());
        holder.textViewDate.setText(currentTask.getDate().toString());
    }

    @Override
    public int getItemCount() {
        return allTasks.size();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewTask;
        private TextView textViewDate;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTask = itemView.findViewById(R.id.textViewTask);
            textViewDate = itemView.findViewById(R.id.textViewDate);
        }
    }
}
