package com.github.romanqed.api.html.loaders;

import com.github.romanqed.api.html.AbstractLinkableHtmlLoader;
import com.github.romanqed.api.html.entites.Request;
import com.github.romanqed.api.interfaces.HtmlBuilder;
import com.github.romanqed.api.util.Checks;
import com.github.romanqed.api.util.Urls;
import com.github.romanqed.concurrent.Task;
import com.github.romanqed.concurrent.TaskFabric;
import kong.unirest.UnirestInstance;

import java.net.URL;

public class RequestLoader extends AbstractLinkableHtmlLoader<Request> {
    public RequestLoader(UnirestInstance client, TaskFabric taskFabric) {
        super(client, taskFabric);
    }

    public RequestLoader(UnirestInstance client) {
        this(client, null);
    }

    public RequestLoader() {
        this(null, null);
    }

    public Task<Request> load(int id) {
        return super.load(Integer.toString(id));
    }

    @Override
    protected URL makeUrl(String id) {
        int parsedId = Integer.parseInt(id);
        return Urls.attachUrl(Urls.REQUESTS, Integer.toString(Checks.requireCorrectValue(parsedId, rawId -> rawId > 0)));
    }

    @Override
    protected HtmlBuilder<Request> getBuilder() {
        return null;
    }
}
