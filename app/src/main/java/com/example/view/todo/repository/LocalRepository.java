package com.example.view.todo.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.view.todo.data.TaskDao;
import com.example.view.todo.data.TaskDatabase;
import com.example.view.todo.model.Task;

import java.util.List;

import static com.example.view.todo.data.TaskDatabase.getInstance;

public class LocalRepository {

    private TaskDao taskDao;

    private LiveData<List<Task>> allTasks;

    public LocalRepository(Application application) {

        TaskDatabase taskDatabase = getInstance(application);

        taskDao = taskDatabase.taskDao();

        allTasks = taskDao.getAllTasks();
    }

    public void insert(Task task) {
        new InsertTaskAsyncTask(taskDao).execute(task);
    }

    public void delete(Task task) {
        new DeleteTaskAsyncTask(taskDao).execute(task);
    }

    public LiveData<List<Task>> getAllTasks() {
        return allTasks;
    }

    private static class InsertTaskAsyncTask extends AsyncTask<Task, Void, Void> {

        private TaskDao taskDao;

        private InsertTaskAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {

            taskDao.insert(tasks[0]);
            return null;
        }
    }

    private static class DeleteTaskAsyncTask extends AsyncTask<Task, Void, Void> {

        private TaskDao taskDao;

        private DeleteTaskAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {

            taskDao.delete(tasks[0]);
            return null;
        }
    }
}
