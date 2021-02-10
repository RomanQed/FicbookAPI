package com.github.romanqed.api.html;

import com.github.romanqed.api.BuildLoader;
import com.github.romanqed.api.interfaces.HtmlBuilder;
import com.github.romanqed.api.util.ParseUtil;
import com.github.romanqed.concurrent.TaskFabric;
import kong.unirest.UnirestInstance;
import org.jsoup.Jsoup;

import java.net.URL;
import java.util.List;

public class CollectionLoader extends BuildLoader<List<?>, HtmlBuilder<?>> {
    private String selector;

    public CollectionLoader(UnirestInstance client, TaskFabric taskFabric) {
        super(client, taskFabric);
        this.selector = "";
    }

    public CollectionLoader(UnirestInstance client) {
        this(client, null);
    }

    public CollectionLoader() {
        this(null, null);
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }

    @Override
    protected List<?> fromResponse(URL url, String body) {
        return ParseUtil.entitiesFromPage(Jsoup.parse(body), builder, selector);
    }
}
