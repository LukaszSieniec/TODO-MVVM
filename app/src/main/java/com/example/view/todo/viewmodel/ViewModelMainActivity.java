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

    private LiveData<List<Task>> allWork;
    private LiveData<List<Task>> allShopping;
    private LiveData<List<Task>> allOther;

    public ViewModelMainActivity(@NonNull Application application) {
        super(application);

        localRepository = new LocalRepository(application);

        allWork = localRepository.getAllWork();
        allShopping = localRepository.getAllShopping();
        allOther = localRepository.getAllOther();
    }

    public void insert(Task task) {
        localRepository.insert(task);
    }

    public void delete(Task task) {
        localRepository.delete(task);
    }

    public LiveData<List<Task>> getAllWork() {
        return allWork;
    }

    public LiveData<List<Task>> getAllShopping() {
        return allShopping;
    }

    public LiveData<List<Task>> getAllOther() {
        return allOther;
    }
}
