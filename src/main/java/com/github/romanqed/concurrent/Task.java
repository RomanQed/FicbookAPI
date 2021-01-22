package com.github.romanqed.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Consumer;

public interface Task<T> extends Callable<T> {
    void async(Consumer<T> success, Consumer<Exception> failure);

    default void async(Consumer<T> success) {
        async(success, null);
    }

    default void async() {
        async(null);
    }

    default T checked(Consumer<Exception> failure) {
        try {
            return call();
        } catch (Exception e) {
            if (failure != null) {
                failure.accept(e);
            }
        }
        return null;
    }

    default T silent() {
        return checked(null);
    }

    Future<?> future();

    ExecutorService getExecutor();
}
