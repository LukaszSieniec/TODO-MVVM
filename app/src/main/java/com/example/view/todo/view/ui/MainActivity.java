package com.example.view.todo.view.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.view.todo.R;
import com.example.view.todo.model.Task;
import com.example.view.todo.view.adapter.CustomSpinnerAdapter;
import com.example.view.todo.view.adapter.TaskAdapter;
import com.example.view.todo.viewmodel.ViewModelMainActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static com.example.view.todo.view.ui.AddTaskActivity.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton buttonAddNewTask;
    private RecyclerView recyclerView;
    private LinearLayout linearLayoutActivityMain;
    private Spinner spinner;

    private CustomSpinnerAdapter customSpinnerAdapter;
    private TaskAdapter taskAdapter;
    private ViewModelMainActivity viewModelMainActivity;

    private static final int ADD_TASK_REQUEST_CODE = 1;
    private static final String CATEGORIES [] = {"Job", "Shopping", "Other"};
    private static final Integer IMAGE = R.drawable.ic_format_list_bulleted;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonAddNewTask = findViewById(R.id.buttonAddNewTask);
        recyclerView = findViewById(R.id.recyclerView);
        linearLayoutActivityMain = findViewById(R.id.linearLayoutActivityMain);
        spinner = findViewById(R.id.spinnerShowCategory);

        buttonAddNewTask.setOnClickListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        taskAdapter = new TaskAdapter();
        recyclerView.setAdapter(taskAdapter);

        customSpinnerAdapter = new CustomSpinnerAdapter(this, CATEGORIES, IMAGE);
        spinner.setAdapter(customSpinnerAdapter);

        viewModelMainActivity = new ViewModelProvider(this).get(ViewModelMainActivity.class);
        viewModelMainActivity.getAllJob().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {

                if(tasks.size() == 0) {

                    linearLayoutActivityMain.setVisibility(View.VISIBLE);

                } else {

                    linearLayoutActivityMain.setVisibility(View.INVISIBLE);
                }

                taskAdapter.setTasks(tasks);
            }
        });

        taskAdapter.setOnItemClickListener(new TaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Task task) {

                viewModelMainActivity.delete(task);
            }
        });
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        if (id == R.id.buttonAddNewTask) {

            Intent intent = new Intent(this, AddTaskActivity.class);
            startActivityForResult(intent, ADD_TASK_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if((requestCode == ADD_TASK_REQUEST_CODE) && (resultCode == RESULT_OK)) {

            String text = data.getStringExtra(TEXT_KEY);
            String date = data.getStringExtra(DATE_KEY);
            String category = data.getStringExtra(CATEGORY_KEY);

            Task task = new Task(text, category, date);
            viewModelMainActivity.insert(task);

            Toast.makeText(this, "Task saved!", Toast.LENGTH_LONG).show();

        } else {

            //TODO
        }
    }
}

