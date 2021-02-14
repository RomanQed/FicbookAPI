package com.github.romanqed.api.loaders;

import com.github.romanqed.api.interfaces.lambdas.AnswerProcessor;
import com.github.romanqed.api.util.Checks;
import com.github.romanqed.api.util.Pair;
import com.github.romanqed.concurrent.BaseTaskFabric;
import com.github.romanqed.concurrent.Task;
import com.github.romanqed.concurrent.TaskFabric;
import kong.unirest.UnirestInstance;

import java.net.URL;
import java.util.List;
import java.util.concurrent.Callable;

public class AsyncLoader extends AbstractLoader {
    protected final TaskFabric fabric;

    public AsyncLoader(UnirestInstance client, TaskFabric fabric) {
        super(client);
        this.fabric = Checks.requireNonNullElse(fabric, new BaseTaskFabric());
    }

    public <T> Task<T> asyncLoad(URL url, List<Pair<String, String>> fields, AnswerProcessor<T> processor) {
        Callable<T> taskBody = () -> processor.handle(url, load(url, fields));
        return fabric.createTask(taskBody);
    }
}
