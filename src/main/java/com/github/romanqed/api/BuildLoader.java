package com.github.romanqed.api;

import com.github.romanqed.api.interfaces.HtmlBuilder;
import com.github.romanqed.concurrent.TaskFabric;
import kong.unirest.UnirestInstance;

public abstract class BuildLoader<T, BUILDER extends HtmlBuilder<?>> extends AbstractDataLoader<T> {
    protected final BUILDER builder;

    public BuildLoader(UnirestInstance client, TaskFabric taskFabric, BUILDER builder) {
        super(client, taskFabric);
        this.builder = builder;
    }
}
