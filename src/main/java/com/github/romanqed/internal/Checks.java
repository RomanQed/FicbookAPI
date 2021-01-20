package com.github.romanqed.internal;

public class Checks {
    public static <T> T requireNonNullElse(T obj, T def) {
        if (obj == null) {
            return def;
        }
        return obj;
    }
}
