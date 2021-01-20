package com.github.romanqed.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

public abstract class AbstractTaskFabric<T> implements TaskFabric<T> {
    protected ExecutorService executor;

    @Override
    public Task<T> createTask(Callable<T> action) {
        return new AbstractTask<T>() {
            @Override
            public T now() throws Exception {
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
