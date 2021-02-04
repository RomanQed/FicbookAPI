package com.github.romanqed.api;

import com.github.romanqed.api.util.Checks;
import com.github.romanqed.concurrent.AbstractTask;
import com.github.romanqed.concurrent.BaseTaskFabric;
import com.github.romanqed.concurrent.Task;
import com.github.romanqed.concurrent.TaskFabric;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.Callable;

public abstract class AbstractDataLoader<T> implements DataLoader<T> {
    protected TaskFabric taskFabric;
    protected OkHttpClient client;

    public AbstractDataLoader(OkHttpClient client, TaskFabric taskFabric) {
        this.client = Checks.requireNonNullElse(client, new OkHttpClient());
        this.taskFabric = Checks.requireNonNullElse(taskFabric, new BaseTaskFabric());
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public Task<T> load(URL url) {
        Callable<T> taskBody = () -> {
            Response response = client.newCall(requestBuilder(url).build()).execute();
            if (response.code() / 100 != 2) {
                throw new IOException("Server returned HTTP response code: " + response.code());
            }
            return fromResponse(url, Checks.requireNonExcept(() -> response.body().string(), ""));
        };
        if (taskFabric != null) {
            return taskFabric.createTask(taskBody);
        } else {
            return new AbstractTask<T>() {
                @Override
                public T call() throws Exception {
                    return taskBody.call();
                }
            };
        }
    }

    @Override
    public Task<T> load(String id) {
        return load(makeUrl(id));
    }

    @Override
    public TaskFabric getTaskFabric() {
        return taskFabric;
    }

    protected abstract T fromResponse(URL url, String body);

    protected Request.Builder requestBuilder(URL link) {
        return new Request.Builder()
                .url(link)
                .method("GET", null);
    }

    protected abstract URL makeUrl(String id);
}
