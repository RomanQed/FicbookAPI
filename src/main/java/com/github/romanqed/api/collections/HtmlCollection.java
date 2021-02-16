package com.github.romanqed.api.collections;

import com.github.romanqed.api.interfaces.EntityCollection;

import java.util.ArrayList;
import java.util.List;

public class HtmlCollection<T> implements EntityCollection<T> {
    private final List<T> items;

    public HtmlCollection() {
        items = new ArrayList<>();
    }

    @Override
    public List<T> getItems() {
        return items;
    }
}
