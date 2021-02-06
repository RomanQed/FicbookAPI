package com.github.romanqed.api.states;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public enum Rating {
    G("g"),
    PG13("pg-13"),
    R("r"),
    NC17("nc-17"),
    NC21("nc-21");

    static final Map<String, Rating> children = toMap();
    final String title;

    Rating(String title) {
        this.title = title;
    }

    public static Map<String, Rating> toMap() {
        Map<String, Rating> ret = new ConcurrentHashMap<>();
        for (Rating rating : values()) {
            ret.put(rating.title, rating);
        }
        return ret;
    }

    public static Rating fromName(String name) {
        return Objects.requireNonNull(children.get(name));
    }
}
