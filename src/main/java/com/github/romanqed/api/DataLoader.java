package com.github.romanqed.api;

import com.github.romanqed.concurrent.Task;
import com.github.romanqed.concurrent.TaskFabric;

import java.net.URL;

public interface DataLoader<T> {
    Task<T> load(URL url);

    Task<T> load(String id);

    TaskFabric getTaskFabric();
}
