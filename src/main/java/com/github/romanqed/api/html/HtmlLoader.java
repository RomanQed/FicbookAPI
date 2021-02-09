package com.github.romanqed.api.html;

import com.github.romanqed.api.AbstractDataLoader;
import com.github.romanqed.api.interfaces.HtmlBuilder;
import com.github.romanqed.concurrent.TaskFabric;
import kong.unirest.UnirestInstance;
import org.jsoup.Jsoup;

import java.net.URL;

public class HtmlLoader extends AbstractDataLoader<Object> {
    private HtmlBuilder<?> builder;

    public HtmlLoader(UnirestInstance client, TaskFabric taskFabric) {
        super(client, taskFabric);
    }

    public HtmlLoader(UnirestInstance client) {
        this(client, null);
    }

    public HtmlLoader() {
        this(null, null);
    }

    public void setBuilder(HtmlBuilder<?> builder) {
        this.builder = builder;
    }

    @Override
    protected Object fromResponse(URL url, String body) {
        return builder.build(url, Jsoup.parse(body));
    }
}
