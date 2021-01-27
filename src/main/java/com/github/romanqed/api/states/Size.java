package com.github.romanqed.api.states;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public enum Size {
    DRABBLE("драббл"),
    MINI("мини"),
    MIDI("миди"),
    MAXI("макси");

    static final Map<String, Size> children = toMap();
    final String title;

    Size(String title) {
        this.title = title;
    }

    public static Map<String, Size> toMap() {
        Map<String, Size> ret = new ConcurrentHashMap<>();
        for (Size size : values()) {
            ret.put(size.getTitle(), size);
        }
        return ret;
    }

    public static Size fromName(String name) {
        return Objects.requireNonNull(children.get(name));
    }

    public String getTitle() {
        return title;
    }
}
