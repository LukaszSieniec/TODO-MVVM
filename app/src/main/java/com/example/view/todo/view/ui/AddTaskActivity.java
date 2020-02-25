package com.example.view.todo.view.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.view.todo.R;

import java.text.DateFormat;
import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {

    private Toolbar toolbar;

    private EditText editTextTask, editTextDate;
    private TextView textViewSelectCategory;
    private Spinner spinnerSelectCategory;

    private ArrayAdapter<CharSequence> spinnerAdapter;

    public static final String TEXT_KEY = "Text";
    public static final String DATE_KEY = "Date";
    public static final String CATEGORY_KEY = "Category";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        toolbar = findViewById(R.id.activityAddTaskToolbar);

        editTextTask = findViewById(R.id.editTextTask);
        editTextDate = findViewById(R.id.editTextDate);
        textViewSelectCategory = findViewById(R.id.textViewSelectCategory);
        spinnerSelectCategory = findViewById(R.id.spinnerSelectCategory);

        editTextDate.setOnClickListener(this);
        spinnerSelectCategory.setOnItemSelectedListener(this);

        setSupportActionBar(toolbar);
        setOptionsToolbar();

        spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.addTaskActivitySpinner,
                android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerSelectCategory.setAdapter(spinnerAdapter);

    }

    private void setOptionsToolbar() {

        if (getSupportActionBar() != null) {

            getSupportActionBar().setTitle("Add a new task");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void saveTask() {

        String text = editTextTask.getText().toString();
        String date = editTextDate.getText().toString();
        String category = textViewSelectCategory.getText().toString();

        if(!text.trim().isEmpty() && (!date.trim().isEmpty())) {

            Intent data = new Intent();
            data.putExtra(TEXT_KEY, text);
            data.putExtra(DATE_KEY, date);
            data.putExtra(CATEGORY_KEY, category);

            setResult(RESULT_OK, data);
        }

        finish();
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

        if(id == R.id.editTextDate) {

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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String category = parent.getItemAtPosition(position).toString();
        textViewSelectCategory.setText(category);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
}
