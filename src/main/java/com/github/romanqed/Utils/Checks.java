package com.github.romanqed.Utils;

public class Checks {
    public static <T> T requireNonNullElse(T obj, T def) {
        if (obj == null) {
            return def;
        }
        return obj;
    }
}
