package com.github.romanqed.api.html;

import com.github.romanqed.api.util.Checks;
import com.github.romanqed.api.util.Urls;
import com.github.romanqed.concurrent.Task;
import com.github.romanqed.concurrent.TaskFabric;
import okhttp3.OkHttpClient;

import java.net.URL;

public class FandomLoader extends AbstractHtmlLoader<Fandom> {
    public FandomLoader(OkHttpClient client, TaskFabric taskFabric) {
        super(client, taskFabric);
    }

    public FandomLoader(OkHttpClient client) {
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
