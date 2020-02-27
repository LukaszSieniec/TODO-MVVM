package com.example.view.todo.view.adapter;

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

import static com.example.view.todo.view.ui.MainActivity.*;


public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> currentTasks = new ArrayList<>();

    private List<Task> allJobTasks = new ArrayList<>();
    private List<Task> allShoppingTasks = new ArrayList<>();
    private List<Task> allOtherTasks = new ArrayList<>();

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

        Task currentTask = currentTasks.get(position);

        holder.textViewTask.setText(currentTask.getText());
        holder.textViewDate.setText(currentTask.getDate());
    }

    @Override
    public int getItemCount() {
        return currentTasks.size();
    }

    public void setCurrentTasks(String category) {

        switch (category) {
            case JOB_CATEGORY:
                this.currentTasks = allJobTasks;
                break;

            case SHOPPING_CATEGORY:
                this.currentTasks = allShoppingTasks;
                break;

            case OTHER_CATEGORY:
                this.currentTasks = allOtherTasks;
                break;
        }

        notifyDataSetChanged();
    }

    public List<Task> getCurrentTasks(String category) {

        switch (category) {
            case JOB_CATEGORY:
                return allJobTasks;

            case SHOPPING_CATEGORY:
                return allShoppingTasks;

            case OTHER_CATEGORY:
                return allOtherTasks;

            default:
                return null;
        }
    }

    public void setAllJobTasks(List<Task> allJobTasks) {
        this.allJobTasks = allJobTasks;
    }

    public void setAllShoppingTasks(List<Task> allShoppingTasks) {
        this.allShoppingTasks = allShoppingTasks;
    }

    public void setAllOtherTasks(List<Task> allOtherTasks) {
        this.allOtherTasks = allOtherTasks;
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

                    if ((id == R.id.checkBoxTask) && (checkBox.isChecked())) {

                        if (onItemClickListener != null) {

                            onItemClickListener.onItemClick(currentTasks.get(position));
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
