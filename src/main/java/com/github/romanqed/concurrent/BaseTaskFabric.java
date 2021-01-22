package com.github.romanqed.concurrent;

import com.github.romanqed.api.util.Checks;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BaseTaskFabric<T> implements TaskFabric<T> {
    protected ExecutorService executor;

    public BaseTaskFabric(ExecutorService executor) {
        this.executor = Checks.requireNonNullElse(executor, Executors.newCachedThreadPool());
    }

    public BaseTaskFabric() {
        this(null);
    }

    @Override
    public Task<T> createTask(Callable<T> action) {
        return new AbstractTask<T>() {
            @Override
            public T call() throws Exception {
                return action.call();
            }

            @Override
            public ExecutorService getExecutor() {
                return executor;
            }
        };
    }


    @Override
    public ExecutorService getExecutor() {
        return executor;
    }

    @Override
    public boolean isQueue() {
        return executor != null;
    }
}
