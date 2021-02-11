package com.github.romanqed.api;

import com.github.romanqed.api.interfaces.DataLoader;
import com.github.romanqed.api.util.Checks;
import com.github.romanqed.api.util.Pair;
import com.github.romanqed.concurrent.AbstractTask;
import com.github.romanqed.concurrent.BaseTaskFabric;
import com.github.romanqed.concurrent.Task;
import com.github.romanqed.concurrent.TaskFabric;
import kong.unirest.*;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.Callable;

public abstract class AbstractDataLoader<T> implements DataLoader<T> {
    protected TaskFabric taskFabric;
    protected UnirestInstance client;

    public AbstractDataLoader(UnirestInstance client, TaskFabric taskFabric) {
        this.client = Checks.requireNonNullElse(client, Unirest.spawnInstance());
        this.taskFabric = Checks.requireNonNullElse(taskFabric, new BaseTaskFabric());
    }

    @Override
    public Task<T> load(URL url, List<Pair<String, String>> fields) {
        Callable<T> taskBody = () -> {
            HttpResponse<String> response = requestBuilder(client, url, fields).asString();
            if (!response.isSuccess()) {
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
    public TaskFabric getTaskFabric() {
        return taskFabric;
    }

    protected abstract T fromResponse(URL url, String body);

    protected HttpRequest<?> requestBuilder(UnirestInstance client, URL url, List<Pair<String, String>> fields) {
        GetRequest ret = client.get(url.toString());
        if (fields != null) {
            fields.forEach(pair -> ret.queryString(pair.getKey(), pair.getValue()));
        }
        return ret;
    }
}
