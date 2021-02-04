package com.github.romanqed.api;

import com.github.romanqed.concurrent.Task;
import com.github.romanqed.concurrent.TaskFabric;

public interface DataLoader<T> {
    Task<T> load(String id);

    TaskFabric getTaskFabric();
}
