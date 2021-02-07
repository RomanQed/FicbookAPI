package com.github.romanqed.api.html;

import com.github.romanqed.concurrent.TaskFabric;
import kong.unirest.UnirestInstance;
import org.jsoup.Jsoup;

import java.net.URL;

public abstract class AbstractHtmlLoader<T extends AbstractHtmlBased> extends AbstractLinkableLoader<T> {
    public AbstractHtmlLoader(UnirestInstance client, TaskFabric taskFabric) {
        super(client, taskFabric);
    }

    @Override
    protected T fromResponse(URL url, String body) {
        return getBuilder().build(url, Jsoup.parse(body));
    }

    protected abstract AbstractHtmlBased.AbstractHtmlBuilder<T> getBuilder();
}
