package com.github.romanqed.api;

import com.github.romanqed.api.util.Checks;
import com.github.romanqed.concurrent.AbstractTask;
import com.github.romanqed.concurrent.BaseTaskFabric;
import com.github.romanqed.concurrent.Task;
import com.github.romanqed.concurrent.TaskFabric;
import kong.unirest.HttpRequest;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestInstance;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.Callable;

public abstract class AbstractDataLoader<T> implements DataLoader<T> {
    protected TaskFabric taskFabric;
    protected UnirestInstance client;

    public AbstractDataLoader(UnirestInstance client, TaskFabric taskFabric) {
        this.client = Checks.requireNonNullElse(client, Unirest.spawnInstance());
        this.taskFabric = Checks.requireNonNullElse(taskFabric, new BaseTaskFabric());
    }

    @Override
    public Task<T> load(URL url) {
        Callable<T> taskBody = () -> {
            HttpResponse<String> response = requestBuilder(client, url).asString();
            if (response.getStatus() / 100 != 2) {
                throw new IOException("Server returned HTTP response code: " + response.getStatus());
            }
            return fromResponse(url, response.getBody());
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

    protected HttpRequest<?> requestBuilder(UnirestInstance client, URL link) {
        return client.get(link.toString());
    }

    protected abstract URL makeUrl(String id);
}
