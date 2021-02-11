package com.github.romanqed.api.enums;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public enum Direction {
    GEN(1, "джен", "ic_gen"),
    HETERO(2, "гет", "ic_het"),
    YAOI(3, "слэш", "ic_slash"),
    YURI(4, "фемслэш", "ic_femslash"),
    ARTICLE(5, "статья", "ic_article"),
    MIXED(6, "смешанная", "ic_mixed"),
    OTHER_RELATIONSHIPS(7, "другиевидыотношений", "ic_other");

    static final Map<String, Direction> children = toMap();
    final List<String> names;
    final int id;

    Direction(int id, String... names) {
        this.id = id;
        this.names = Arrays.asList(names);
    }

    public static Map<String, Direction> toMap() {
        Map<String, Direction> ret = new ConcurrentHashMap<>();
        for (Direction direction : values()) {
            for (String name : direction.names) {
                ret.put(name, direction);
            }
        }
        return ret;
    }

    public int getId() {
        return id;
    }

    public static Direction fromName(String name) {
        return Objects.requireNonNull(children.get(name));
    }
}
