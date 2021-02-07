package com.github.romanqed.api.html.loaders;

import com.github.romanqed.api.html.AbstractLinkableHtmlLoader;
import com.github.romanqed.api.html.entites.Tag;
import com.github.romanqed.api.interfaces.HtmlBuilder;
import com.github.romanqed.api.util.Checks;
import com.github.romanqed.api.util.Urls;
import com.github.romanqed.concurrent.Task;
import com.github.romanqed.concurrent.TaskFabric;
import kong.unirest.UnirestInstance;

import java.net.URL;

public class TagLoader extends AbstractLinkableHtmlLoader<Tag> {
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
    protected HtmlBuilder<Tag> getBuilder() {
        return Tag.BUILDER;
    }

    @Override
    protected URL makeUrl(String id) {
        int parsedId = Integer.parseInt(id);
        return Urls.attachUrl(Urls.TAGS, Integer.toString(Checks.requireCorrectValue(parsedId, rawId -> rawId > 0)));
    }
}
