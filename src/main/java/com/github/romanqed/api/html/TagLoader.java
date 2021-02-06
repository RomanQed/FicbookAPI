package com.github.romanqed.api.html;

import com.github.romanqed.api.util.Checks;
import com.github.romanqed.api.util.Urls;
import com.github.romanqed.concurrent.Task;
import com.github.romanqed.concurrent.TaskFabric;
import kong.unirest.UnirestInstance;

import java.net.URL;

public class TagLoader extends AbstractHtmlLoader<Tag> {
    public TagLoader(UnirestInstance client, TaskFabric taskFabric) {
        super(client, taskFabric);
    }

    public TagLoader(UnirestInstance client) {
        this(client, null);
    }

    public TagLoader() {
        this(null, null);
    }

    public Task<Tag> load(int id) {
        return super.load(Integer.toString(id));
    }

    @Override
    protected Class<Tag> getElementClass() {
        return Tag.class;
    }

    @Override
    protected URL makeUrl(String id) {
        int parsedId = Integer.parseInt(id);
        return Urls.attachUrl(Urls.TAGS, Integer.toString(Checks.requireCorrectValue(parsedId, rawId -> rawId > 0)));
    }
}
