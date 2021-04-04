package com.github.romanqed.api.exchangers;

import com.github.romanqed.api.interfaces.lambdas.ResponseProcessor;
import com.github.romanqed.api.util.Checks;
import com.github.romanqed.api.util.Pair;
import com.github.romanqed.concurrent.BaseTaskFabric;
import com.github.romanqed.concurrent.Task;
import com.github.romanqed.concurrent.TaskFabric;
import kong.unirest.UnirestInstance;

import java.net.URL;
import java.util.List;
import java.util.concurrent.Callable;

public class AsyncExchanger extends AbstractExchanger {
    protected final TaskFabric fabric;

    public AsyncExchanger(UnirestInstance client, TaskFabric fabric) {
        super(client);
        this.fabric = Checks.requireNonNullElse(fabric, new BaseTaskFabric());
    }

    public <T> Task<T> asyncLoad(URL url, List<Pair<String, String>> fields, ResponseProcessor<T> processor) {
        Callable<T> taskBody = () -> processor.handle(url, load(url, fields));
        return fabric.createTask(taskBody);
    }
}
