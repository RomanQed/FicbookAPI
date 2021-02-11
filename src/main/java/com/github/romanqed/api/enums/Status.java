package com.github.romanqed.api.enums;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public enum Status {
    IN_PROGRESS(1, "впроцессе", "ic_in-progress"),
    COMPLETED(2, "закончен", "ic_done"),
    FROZEN(3, "заморожен", "ic_freezed");

    static final Map<String, Status> children = toMap();
    final List<String> names;
    final int id;

    Status(int id, String... names) {
        this.id = id;
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

    public int getId() {
        return id;
    }

    public static Status fromName(String name) {
        return Objects.requireNonNull(children.get(name));
    }
}
