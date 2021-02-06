package com.github.romanqed.api.states;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public enum Status {
    COMPLETED("закончен", "ic_done"),
    IN_PROGRESS("впроцессе", "ic_in-progress"),
    FROZEN("заморожен", "ic_freezed");

    static final Map<String, Status> children = toMap();
    final List<String> names;

    Status(String... names) {
        this.names = Arrays.asList(names);
    }

    public static Map<String, Status> toMap() {
        Map<String, Status> ret = new ConcurrentHashMap<>();
        for (Status status : values()) {
            for (String name : status.names) {
                ret.put(name, status);
            }
        }
        return ret;
    }

    public static Status fromName(String name) {
        return Objects.requireNonNull(children.get(name));
    }
}
