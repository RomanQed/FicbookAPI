package com.github.romanqed.api.states;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public enum Direction {
    GEN("джен", "ic_gen"),
    HETERO("гет", "ic_het"),
    YAOI("слэш", "ic_slash"),
    YURI("фемслэш", "ic_femslash"),
    MIXED("смешанная", "ic_mixed"),
    OTHER_RELATIONSHIPS("ic_other"),
    ARTICLE("ic_article");

    static final Map<String, Direction> children = toMap();
    final List<String> names;

    Direction(String... names) {
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

    public static Direction fromName(String name) {
        return Objects.requireNonNull(children.get(name));
    }
}
