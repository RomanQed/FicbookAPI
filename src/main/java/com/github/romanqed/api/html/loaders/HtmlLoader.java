package com.github.romanqed.api.html.loaders;

import com.github.romanqed.api.BuildLoader;
import com.github.romanqed.api.interfaces.HtmlPageBuilder;
import com.github.romanqed.concurrent.TaskFabric;
import kong.unirest.UnirestInstance;
import org.jsoup.Jsoup;

import java.net.URL;

public class HtmlLoader extends BuildLoader<Object, HtmlPageBuilder<?>> {
    public HtmlLoader(UnirestInstance client, TaskFabric taskFabric, HtmlPageBuilder<?> builder) {
        super(client, taskFabric, builder);
    }

    public HtmlLoader(UnirestInstance client, HtmlPageBuilder<?> builder) {
        this(client, null, builder);
    }

    public HtmlLoader(HtmlPageBuilder<?> builder) {
        this(null, null, builder);
    }

    @Override
    protected Object fromResponse(URL url, String body) {
        return builder.build(url, Jsoup.parse(body));
    }
}
