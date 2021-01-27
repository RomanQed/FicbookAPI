package com.github.romanqed.api.interfaces;

import com.github.romanqed.concurrent.Task;
import com.github.romanqed.concurrent.TaskFabric;
import okhttp3.Response;

import java.net.URL;

public interface Linkable {
    URL getLink();

    Task<Response> load(TaskFabric<Response> taskFabric);

    default Task<Response> load() {
        return load(null);
    }

    default Response loadNow() {
        return load(null).silent();
    }

    boolean fullLoaded();
}
