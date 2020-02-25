package com.example.view.todo.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.view.todo.model.Task;
import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    void insert(Task task);

    @Delete
    void delete(Task task);

    @Query("SELECT * FROM task_table")
    LiveData<List<Task>> getAllJob();

    @Query("SELECT * FROM task_table WHERE category = 'Shopping'")
    LiveData<List<Task>> getAllShopping();

    @Query("SELECT * FROM task_table WHERE category = 'Other'")
    LiveData<List<Task>> getAllOther();
}
