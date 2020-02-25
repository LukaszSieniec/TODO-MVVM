package com.example.view.todo.view.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.view.todo.R;
import com.example.view.todo.view.adapter.TaskAdapter;
import com.example.view.todo.viewmodel.ViewModelMainActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton buttonAddNewTask;
    private RecyclerView recyclerView;
    private LinearLayout linearLayoutActivityMain;

    private TaskAdapter taskAdapter;
    private ViewModelMainActivity viewModelMainActivity;

    private static final int ADD_TASK_REQUEST = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonAddNewTask = findViewById(R.id.buttonAddNewTask);
        recyclerView = findViewById(R.id.recyclerView);
        linearLayoutActivityMain = findViewById(R.id.linearLayoutActivityMain);

        buttonAddNewTask.setOnClickListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        taskAdapter = new TaskAdapter();
        recyclerView.setAdapter(taskAdapter);

        viewModelMainActivity = new ViewModelProvider(this).get(ViewModelMainActivity.class);
        viewModelMainActivity.getAllJob()

    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        if (id == R.id.buttonAddNewTask) {

            Intent intent = new Intent(this, AddTaskActivity.class);
            startActivityForResult(intent, ADD_TASK_REQUEST);
        }
    }
}

