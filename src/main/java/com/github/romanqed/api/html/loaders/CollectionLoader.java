package com.github.romanqed.api.html.loaders;

import com.github.romanqed.api.BuildLoader;
import com.github.romanqed.api.interfaces.HtmlBuilder;
import com.github.romanqed.api.util.Checks;
import com.github.romanqed.api.util.ParseUtil;
import com.github.romanqed.concurrent.TaskFabric;
import kong.unirest.UnirestInstance;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.net.URL;
import java.util.List;
import java.util.function.Consumer;

public class CollectionLoader extends BuildLoader<List<?>, HtmlBuilder<?>> {
    private final String selector;
    private Consumer<Document> onParse = null;

    public CollectionLoader(UnirestInstance client, TaskFabric taskFabric, HtmlBuilder<?> builder, String selector) {
        super(client, taskFabric, builder);
        this.selector = selector;
    }

    public CollectionLoader(UnirestInstance client, HtmlBuilder<?> builder, String selector) {
        this(client, null, builder, selector);
    }

    public CollectionLoader(HtmlBuilder<?> builder, String selector) {
        this(null, null, builder, selector);
    }

    public void onParse(Consumer<Document> onParse) {
        this.onParse = Checks.requireNonNullElse(onParse, System.out::println);
    }

    @Override
    protected List<?> fromResponse(URL url, String body) {
        Document page = Jsoup.parse(body);
        if (onParse != null) {
            onParse.accept(page);
        }
        return ParseUtil.entitiesFromPage(page, builder, selector);
    }
}
