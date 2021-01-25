package com.github.romanqed.api.interfaces;

import com.github.romanqed.concurrent.Task;
import com.github.romanqed.concurrent.TaskFabric;

import java.net.URL;

public interface Linkable {
    URL getLink();

    Task<Void> load(TaskFabric<Void> taskFabric);

    default Task<Void> load() {
        return load(null);
    }

    default void loadNow() {
        load(null).silent();
    }

    boolean fullLoaded();
}
