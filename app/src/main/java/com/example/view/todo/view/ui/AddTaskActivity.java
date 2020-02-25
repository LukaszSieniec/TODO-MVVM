package com.example.view.todo.view.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.view.todo.R;

import java.text.DateFormat;
import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private Toolbar toolbar;

    private EditText editTextTask, editTextDate;
    private TextView textViewNameList;
    private Spinner spinnerSelectList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        toolbar = findViewById(R.id.activityAddTaskToolbar);

        editTextTask = findViewById(R.id.editTextTask);
        editTextDate = findViewById(R.id.editTextDate);
        textViewNameList = findViewById(R.id.textViewNameList);
        spinnerSelectList = findViewById(R.id.spinnerSelectList);

        editTextDate.setOnClickListener(this);

        setSupportActionBar(toolbar);
        setOptionsToolbar();

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.addTaskActivitySpinner,
                android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerSelectList.setAdapter(spinnerAdapter);

    }

    private void setOptionsToolbar() {

        if (getSupportActionBar() != null) {

            getSupportActionBar().setTitle("Add a new task");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_task_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        switch (id) {

            case R.id.save_task:

                saveTask();
                return true;

            case android.R.id.home:

                back();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        switch (id) {

            case R.id.editTextDate:

                DatePickerFragment datePickerDialog = new DatePickerFragment();
                datePickerDialog.show(getSupportFragmentManager(), "Date Picker");
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        editTextDate.setText(currentDate);
    }

    private void saveTask() {

    }

    private void back() {

        String text = editTextTask.getText().toString();
        String date = editTextDate.getText().toString();

        if (text.trim().isEmpty() && (date.trim().isEmpty())) {

            finish();

        } else {

            buildAlertDialog();
        }
    }

    private void buildAlertDialog() {

        new AlertDialog.Builder(this)
                .setTitle("Warning!")
                .setMessage("Are you sure you want to leave without saving?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
