package com.github.romanqed.api.html;

import com.github.romanqed.api.AbstractDataLoader;
import com.github.romanqed.api.interfaces.Linkable;
import com.github.romanqed.concurrent.Task;
import com.github.romanqed.concurrent.TaskFabric;
import okhttp3.OkHttpClient;

public abstract class AbstractLinkableLoader<T> extends AbstractDataLoader<T> {
    public AbstractLinkableLoader(OkHttpClient client, TaskFabric taskFabric) {
        super(client, taskFabric);
    }

    public Task<T> load(Linkable prototype) {
        return load(prototype.getUrl());
    }
}
