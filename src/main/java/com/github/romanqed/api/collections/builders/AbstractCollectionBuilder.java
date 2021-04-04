package com.github.romanqed.api.collections.builders;

import com.github.romanqed.api.interfaces.CollectionBuilder;
import com.github.romanqed.api.interfaces.HtmlBuilder;

public abstract class AbstractCollectionBuilder<T> implements CollectionBuilder<T> {
    protected final String query;
    protected final HtmlBuilder<T> builder;

    protected AbstractCollectionBuilder(String query, HtmlBuilder<T> builder) {
        this.builder = builder;
        this.query = query;
    }
}
