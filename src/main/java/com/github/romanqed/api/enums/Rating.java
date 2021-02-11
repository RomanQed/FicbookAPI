package com.github.romanqed.api.enums;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public enum Rating {
    G(5, "g"),
    PG13(6, "pg-13"),
    R(7, "r"),
    NC17(8, "nc-17"),
    NC21(9, "nc-21");

    static final Map<String, Rating> children = toMap();
    final String title;
    final int id;

    Rating(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public static Map<String, Rating> toMap() {
        Map<String, Rating> ret = new ConcurrentHashMap<>();
        for (Rating rating : values()) {
            ret.put(rating.title, rating);
        }
        return ret;
    }

    public int getId() {
        return id;
    }

    public static Rating fromName(String name) {
        return Objects.requireNonNull(children.get(name));
    }
}
