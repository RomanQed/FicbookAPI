package com.github.romanqed.api.html.loaders;

import com.github.romanqed.api.html.AbstractLinkableHtmlLoader;
import com.github.romanqed.api.html.entites.Fandom;
import com.github.romanqed.api.interfaces.HtmlBuilder;
import com.github.romanqed.api.util.Checks;
import com.github.romanqed.api.util.Urls;
import com.github.romanqed.concurrent.Task;
import com.github.romanqed.concurrent.TaskFabric;
import kong.unirest.UnirestInstance;

import java.net.URL;

public class FandomHtmlLoader extends AbstractLinkableHtmlLoader<Fandom> {
    public FandomHtmlLoader(UnirestInstance client, TaskFabric taskFabric) {
        super(client, taskFabric);
    }

    public FandomHtmlLoader(UnirestInstance client) {
        this(client, null);
    }

    public FandomHtmlLoader() {
        this(null, null);
    }

    public Task<Fandom> loadByRefs(String... refs) {
        return super.load(String.join("/", refs));
    }

    @Override
    protected HtmlBuilder<Fandom> getBuilder() {
        return Fandom.BUILDER;
    }

    @Override
    protected URL makeUrl(String id) {
        return Urls.attachUrl(Urls.FANDOMS, Checks.requireCorrectValue(id, String::isEmpty));
    }
}
