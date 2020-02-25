package com.example.view.todo.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.view.todo.model.Task;

@Database(entities = {Task.class}, version = 1, exportSchema = false)
public abstract class TaskDatabase extends RoomDatabase {

    private static final String databaseName = "tasks_database";
    private static TaskDatabase singleInstance;

    public abstract TaskDao taskDao();

    public static TaskDatabase getInstance(Context context) {

        if (singleInstance == null) {

            singleInstance = Room.databaseBuilder(context.getApplicationContext(), TaskDatabase.class, databaseName)
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return singleInstance;
    }
}
