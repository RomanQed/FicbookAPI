package com.github.romanqed.api.util;

import java.util.concurrent.Callable;
import java.util.function.Predicate;

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

    public static String requireNonNullString(String string) {
        return requireNonNullElse(string, "");
    }

    public static <T> T requireCorrectValue(T value, Predicate<T> predicate) {
        if (!predicate.test(value)) {
            throw new IllegalArgumentException("Incorrect " + value.getClass().getCanonicalName() + " value " + value);
        }
        return value;
    }

    public static <T> T requireCorrectValueElse(T value, Predicate<T> predicate, T def) {
        if (!predicate.test(value)) {
            return def;
        }
        return value;
    }
}
