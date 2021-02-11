package com.github.romanqed.api.interfaces;

import com.github.romanqed.api.util.Pair;
import com.github.romanqed.concurrent.Task;
import com.github.romanqed.concurrent.TaskFabric;

import java.net.URL;
import java.util.List;

public interface DataLoader<T> {
    Task<T> load(URL url, List<Pair<String, String>> fields);

    default Task<T> load(URL url) {
        return load(url, null);
    }

    TaskFabric getTaskFabric();
}
