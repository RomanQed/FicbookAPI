package com.github.romanqed.api;

import com.github.romanqed.api.interfaces.HtmlBuilder;
import com.github.romanqed.concurrent.TaskFabric;
import kong.unirest.UnirestInstance;

public abstract class BuildLoader<T> extends AbstractDataLoader<T> {
    protected HtmlBuilder<?> builder;

    public BuildLoader(UnirestInstance client, TaskFabric taskFabric) {
        super(client, taskFabric);
    }

    public void setBuilder(HtmlBuilder<?> builder) {
        this.builder = builder;
    }
}
