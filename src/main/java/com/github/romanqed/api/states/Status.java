package com.github.romanqed.api.states;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public enum Status {
    COMPLETED("закончен"),
    IN_PROGRESS("в процессе"),
    FROZEN("заморожен");

    static final Map<String, Status> children = toMap();
    final String title;

    Status(String title) {
        this.title = title;
    }

    public static Map<String, Status> toMap() {
        Map<String, Status> ret = new ConcurrentHashMap<>();
        for (Status status : values()) {
            ret.put(status.getTitle(), status);
        }
        return ret;
    }

    public static Status fromName(String name) {
        return Objects.requireNonNull(children.get(name));
    }

    public String getTitle() {
        return title;
    }
}
