package com.example.view.todo.view.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.view.todo.R;
import com.example.view.todo.model.Task;
import com.example.view.todo.view.adapter.CustomSpinnerAdapter;
import com.example.view.todo.view.adapter.TaskAdapter;
import com.example.view.todo.viewmodel.ViewModelMainActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static com.example.view.todo.view.ui.AddTaskActivity.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Spinner.OnItemSelectedListener {

    private FloatingActionButton buttonAddNewTask;
    private RecyclerView recyclerView;
    private LinearLayout linearLayoutActivityMain;
    private Spinner spinner;

    private CustomSpinnerAdapter customSpinnerAdapter;
    private TaskAdapter taskAdapter;
    private ViewModelMainActivity viewModelMainActivity;

    private String currentCategory = "";

    private static final int ADD_TASK_REQUEST_CODE = 1;

    public static final String JOB_CATEGORY = "Job";
    public static final String SHOPPING_CATEGORY = "Shopping";
    public static final String OTHER_CATEGORY = "Other";

    private static final String CATEGORIES [] = {JOB_CATEGORY, SHOPPING_CATEGORY, OTHER_CATEGORY};
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
        spinner.setOnItemSelectedListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        taskAdapter = new TaskAdapter();
        recyclerView.setAdapter(taskAdapter);

        customSpinnerAdapter = new CustomSpinnerAdapter(this, CATEGORIES, IMAGE);
        spinner.setAdapter(customSpinnerAdapter);

        getAllTasks();

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

            Toast.makeText(this, "Task saved in " + category + " list!", Toast.LENGTH_LONG).show();

        } else {

            buildAlertDialog();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        currentCategory = parent.getItemAtPosition(position).toString();

        if(taskAdapter.getCurrentTasks(currentCategory).size() == 0) {

            linearLayoutActivityMain.setVisibility(View.VISIBLE);

        } else {

            linearLayoutActivityMain.setVisibility(View.INVISIBLE);
        }

        taskAdapter.setCurrentTasks(currentCategory);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    private void getAllTasks() {

        viewModelMainActivity = new ViewModelProvider(this).get(ViewModelMainActivity.class);
        viewModelMainActivity.getAllTasks().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {

                List<Task> allJobTasks = new ArrayList<>();
                List<Task> allShoppingTasks = new ArrayList<>();
                List<Task> allOtherTasks = new ArrayList<>();

                for(int i = 0; i < tasks.size(); i ++) {

                    if(tasks.get(i).getCategory().equals(JOB_CATEGORY)) {

                        allJobTasks.add(tasks.get(i));

                    } else if (tasks.get(i).getCategory().equals(SHOPPING_CATEGORY)) {

                        allShoppingTasks.add(tasks.get(i));

                    } else if(tasks.get(i).getCategory().equals(OTHER_CATEGORY)) {

                        allOtherTasks.add(tasks.get(i));
                    }
                }

                taskAdapter.setAllJobTasks(allJobTasks);
                taskAdapter.setAllShoppingTasks(allShoppingTasks);
                taskAdapter.setAllOtherTasks(allOtherTasks);

                showOrHideImageAndText(allJobTasks, JOB_CATEGORY);
                showOrHideImageAndText(allShoppingTasks, SHOPPING_CATEGORY);
                showOrHideImageAndText(allOtherTasks, OTHER_CATEGORY);

                taskAdapter.setCurrentTasks(currentCategory);

            }
        });
    }

    private void showOrHideImageAndText(List<Task> tasks, String currentCategory) {

        if((tasks.size() == 0) && (this.currentCategory.equals(currentCategory))) {

            linearLayoutActivityMain.setVisibility(View.VISIBLE);

        } else if ((tasks.size() > 0) && (this.currentCategory.equals(currentCategory))){

            linearLayoutActivityMain.setVisibility(View.INVISIBLE);
        }
    }

    private void buildAlertDialog() {

        new AlertDialog.Builder(this)
                .setTitle("Unsaved task!")
                .setMessage("You didn't enter all the information. Do you want to retry the operation?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
                        startActivityForResult(intent, ADD_TASK_REQUEST_CODE);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}

