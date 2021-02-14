package com.github.romanqed.api;

import com.github.romanqed.api.interfaces.Identifiable;

public abstract class AbstractIdentifiable implements Identifiable {
    protected final String id;

    protected AbstractIdentifiable(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "[ID] " + id;
    }
}
