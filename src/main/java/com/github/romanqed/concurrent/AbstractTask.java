package com.github.romanqed.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Consumer;

public abstract class AbstractTask<T> implements Task<T> {
    protected Future<?> result;

    @Override
    public void async(Consumer<T> success, Consumer<Exception> failure) {
        ExecutorService executor = getExecutor();
        if (executor == null) {
            return;
        }
        result = executor.submit(() -> {
            try {
                T ret = call();
                if (success != null) {
                    success.accept(ret);
                }
            } catch (Exception e) {
                if (failure != null) {
                    failure.accept(e);
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public ExecutorService getExecutor() {
        return null;
    }

    @Override
    public Future<?> future() {
        if (result == null) {
            throw new IllegalStateException("The task was not added to the queue");
        }
        return result;
    }
}
