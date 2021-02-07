package com.github.romanqed.api.html;

import com.github.romanqed.api.interfaces.Linkable;
import com.github.romanqed.concurrent.Task;
import com.github.romanqed.concurrent.TaskFabric;
import kong.unirest.UnirestInstance;

public abstract class AbstractLinkableHtmlLoader<T extends Linkable> extends AbstractHtmlLoader<T> {
    public AbstractLinkableHtmlLoader(UnirestInstance client, TaskFabric taskFabric) {
        super(client, taskFabric);
    }

    public Task<T> load(Linkable prototype) {
        return load(prototype.getUrl());
    }
}
