package com.github.romanqed.api.enums;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public enum Size {
    DRABBLE(1, "драббл"),
    MINI(2, "мини"),
    MIDI(3, "миди"),
    MAXI(4, "макси");

    static final Map<String, Size> children = toMap();
    final String title;
    final int id;

    Size(int id, String title) {
        this.id = id;
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

    public int getId() {
        return id;
    }
}
