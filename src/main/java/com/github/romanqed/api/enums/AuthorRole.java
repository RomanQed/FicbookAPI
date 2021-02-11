package com.github.romanqed.api.enums;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public enum AuthorRole {
    AUTHOR("автор"),
    COAUTHOR("соавтор"),
    BETA("бета"),
    GAMMA("гамма"),
    TRANSLATOR("переводчик");

    static final Map<String, AuthorRole> children = toMap();
    final List<String> names;

    AuthorRole(String... names) {
        this.names = Arrays.asList(names);
    }

    public static Map<String, AuthorRole> toMap() {
        Map<String, AuthorRole> ret = new ConcurrentHashMap<>();
        for (AuthorRole role : values()) {
            for (String name : role.names) {
                ret.put(name, role);
            }
        }
        return ret;
    }

    public static AuthorRole fromName(String name) {
        return Objects.requireNonNull(children.get(name));
    }
}
