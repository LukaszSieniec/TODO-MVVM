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

    private static final String JOB_CATEGORY = "Job";
    private static final String SHOPPING_CATEGORY = "Shopping";
    private static final String OTHER_CATEGORY = "Other";

    private TaskDao taskDao;

    private LiveData<List<Task>> allJob;
    private LiveData<List<Task>> allShopping;
    private LiveData<List<Task>> allOther;

    public LocalRepository(Application application) {

        TaskDatabase taskDatabase = getInstance(application);

        taskDao = taskDatabase.taskDao();

        allJob = taskDao.getAllTasks(JOB_CATEGORY);
        allShopping = taskDao.getAllTasks(SHOPPING_CATEGORY);
        allOther = taskDao.getAllTasks(OTHER_CATEGORY);
    }

    public void insert(Task task) {
        new InsertTaskAsyncTask(taskDao).execute(task);
    }

    public void delete(Task task) {
        new DeleteTaskAsyncTask(taskDao).execute(task);
    }

    public LiveData<List<Task>> getAllJob() {
        return allJob;
    }

    public LiveData<List<Task>> getAllShopping() {
        return allShopping;
    }

    public LiveData<List<Task>> getAllOther() {
        return allOther;
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
