package com.example.view.todo.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.view.todo.model.Task;
import com.example.view.todo.repository.LocalRepository;

import java.util.List;

public class ViewModelMainActivity extends AndroidViewModel {

    private LocalRepository localRepository;

    private LiveData<List<Task>> allTasks;

    public ViewModelMainActivity(@NonNull Application application) {
        super(application);

        localRepository = new LocalRepository(application);

        allTasks = localRepository.getAllTasks();
    }

    public void insert(Task task) {
        localRepository.insert(task);
    }

    public void delete(Task task) {
        localRepository.delete(task);
    }

    public LiveData<List<Task>> getAllTasks() {
        return allTasks;
    }
}
