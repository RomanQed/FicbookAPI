package com.github.romanqed.api.html;

import com.github.romanqed.api.util.Checks;
import com.github.romanqed.api.util.Urls;
import com.github.romanqed.concurrent.Task;
import com.github.romanqed.concurrent.TaskFabric;
import kong.unirest.UnirestInstance;

import java.net.URL;

public class FandomLoader extends AbstractHtmlLoader<Fandom> {
    public FandomLoader(UnirestInstance client, TaskFabric taskFabric) {
        super(client, taskFabric);
    }

    public FandomLoader(UnirestInstance client) {
        this(client, null);
    }

    public FandomLoader() {
        this(null, null);
    }

    public Task<Fandom> loadByRefs(String... refs) {
        return super.load(String.join("/", refs));
    }

    @Override
    protected Class<Fandom> getElementClass() {
        return Fandom.class;
    }

    @Override
    protected URL makeUrl(String id) {
        return Urls.attachUrl(Urls.FANDOMS, Checks.requireCorrectValue(id, String::isEmpty));
    }
}
