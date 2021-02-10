package com.github.romanqed.api.interfaces;

import com.github.romanqed.concurrent.Task;
import com.github.romanqed.concurrent.TaskFabric;

import java.net.URL;
import java.util.Map;

public interface DataLoader<T> {
    Task<T> load(URL url, Map<String, String> fields);

    default Task<T> load(URL url) {
        return load(url, null);
    }

    TaskFabric getTaskFabric();
}
