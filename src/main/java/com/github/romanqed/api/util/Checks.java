package com.github.romanqed.api.util;

import java.util.concurrent.Callable;

public class Checks {
    public static <T> T requireNonNullElse(T obj, T def) {
        if (obj == null) {
            return def;
        }
        return obj;
    }

    public static <T> T requireNonExcept(Callable<T> expression, T def) {
        try {
            return expression.call();
        } catch (Exception e) {
            return def;
        }
    }
}
