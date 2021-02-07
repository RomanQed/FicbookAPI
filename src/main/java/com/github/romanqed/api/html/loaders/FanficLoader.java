package com.github.romanqed.api.html.loaders;

import com.github.romanqed.api.html.AbstractHtmlBased;
import com.github.romanqed.api.html.AbstractHtmlLoader;
import com.github.romanqed.api.html.entites.Fanfic;
import com.github.romanqed.api.interfaces.HtmlBuilder;
import com.github.romanqed.api.util.Checks;
import com.github.romanqed.api.util.Urls;
import com.github.romanqed.concurrent.Task;
import com.github.romanqed.concurrent.TaskFabric;
import kong.unirest.UnirestInstance;

import java.net.URL;

public class FanficLoader extends AbstractHtmlLoader<Fanfic> {
    public FanficLoader(UnirestInstance client, TaskFabric taskFabric) {
        super(client, taskFabric);
    }

    public FanficLoader(UnirestInstance client) {
        this(client, null);
    }

    public FanficLoader() {
        this(null, null);
    }

    public Task<Fanfic> load(int id) {
        return super.load(Integer.toString(id));
    }

    @Override
    protected URL makeUrl(String id) {
        int parsedId = Integer.parseInt(id);
        return Urls.attachUrl(Urls.FANFIC, Integer.toString(Checks.requireCorrectValue(parsedId, rawId -> rawId > 0)));
    }

    @Override
    protected HtmlBuilder<Fanfic> getBuilder() {
        return Fanfic.BUILDER;
    }
}
