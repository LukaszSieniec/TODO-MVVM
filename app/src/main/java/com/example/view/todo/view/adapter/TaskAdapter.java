package com.example.view.todo.view.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.view.todo.R;
import com.example.view.todo.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> allTasks = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

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
        holder.textViewDate.setText(currentTask.getDate());
    }

    @Override
    public int getItemCount() {
        return allTasks.size();
    }

    public void setTasks(List<Task> allTasks) {
        this.allTasks = allTasks;
        notifyDataSetChanged();
    }

     class TaskViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewTask;
        private TextView textViewDate;
        private CheckBox checkBox;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewTask = itemView.findViewById(R.id.textViewTask);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            checkBox = itemView.findViewById(R.id.checkBoxTask);

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int position = getAdapterPosition();
                    int id = view.getId();

                    if((id == R.id.checkBoxTask) && (checkBox.isChecked())) {

                        if(onItemClickListener != null) {

                            onItemClickListener.onItemClick(allTasks.get(position));
                        }
                    }

                    checkBox.setChecked(false);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Task task);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
