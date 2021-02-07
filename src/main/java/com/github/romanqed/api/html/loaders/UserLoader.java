package com.github.romanqed.api.html.loaders;

import com.github.romanqed.api.html.AbstractHtmlBased;
import com.github.romanqed.api.html.AbstractHtmlLoader;
import com.github.romanqed.api.html.entites.User;
import com.github.romanqed.api.interfaces.HtmlBuilder;
import com.github.romanqed.api.util.Checks;
import com.github.romanqed.api.util.Urls;
import com.github.romanqed.concurrent.Task;
import com.github.romanqed.concurrent.TaskFabric;
import kong.unirest.UnirestInstance;

import java.net.URL;

public class UserLoader extends AbstractHtmlLoader<User> {
    public UserLoader(UnirestInstance client, TaskFabric taskFabric) {
        super(client, taskFabric);
    }

    public UserLoader(UnirestInstance client) {
        this(client, null);
    }

    public UserLoader() {
        this(null, null);
    }

    public Task<User> load(int id) {
        return load(Integer.toString(id));
    }

    @Override
    protected HtmlBuilder<User> getBuilder() {
        return User.BUILDER;
    }

    @Override
    protected URL makeUrl(String id) {
        int parsedId = Integer.parseInt(id);
        return Urls.attachUrl(Urls.AUTHORS, Integer.toString(Checks.requireCorrectValue(parsedId, rawId -> rawId > 0)));
    }
}
