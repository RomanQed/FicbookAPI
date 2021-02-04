package com.github.romanqed.api.html;

import com.github.romanqed.api.AbstractDataLoader;
import com.github.romanqed.api.interfaces.Linkable;
import com.github.romanqed.api.util.Checks;
import com.github.romanqed.concurrent.Task;
import com.github.romanqed.concurrent.TaskFabric;
import okhttp3.OkHttpClient;

import java.net.URL;

public abstract class AbstractHtmlLoader<T extends AbstractHtmlBased> extends AbstractDataLoader<T> {
    public AbstractHtmlLoader(OkHttpClient client, TaskFabric taskFabric) {
        super(client, taskFabric);
    }

    public Task<T> load(Linkable prototype) {
        return load(prototype.getUrl());
    }

    @Override
    protected T fromResponse(URL url, String body) {
        Class<T> tClass = getElementClass();
        T ret = Checks.requireNonExcept(() -> tClass.getDeclaredConstructor(URL.class).newInstance(url), null);
        if (ret == null) {
            return null;
        }
        ret.fromPage(body);
        return ret;
    }

    protected abstract Class<T> getElementClass();
}
