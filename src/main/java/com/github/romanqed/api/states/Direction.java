package com.github.romanqed.api.states;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public enum Direction {
    GEN("джен"),
    HETERO("гет"),
    YAOI("слэш"),
    YURI("фемслэш"),
    MIXED("смешанная"),
    OTHER_RELATIONSHIPS("другиевидыотношений"),
    ARTICLE("статья");

    static final Map<String, Direction> children = toMap();
    final String title;

    Direction(String title) {
        this.title = title;
    }

    public static Map<String, Direction> toMap() {
        Map<String, Direction> ret = new ConcurrentHashMap<>();
        for (Direction direction : values()) {
            ret.put(direction.getTitle(), direction);
        }
        return ret;
    }

    public static Direction fromName(String name) {
        return Objects.requireNonNull(children.get(name));
    }

    public String getTitle() {
        return title;
    }
}
