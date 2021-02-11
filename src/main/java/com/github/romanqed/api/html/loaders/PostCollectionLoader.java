package com.github.romanqed.api.html.loaders;

import com.github.romanqed.api.interfaces.HtmlBuilder;
import com.github.romanqed.api.util.Pair;
import com.github.romanqed.concurrent.TaskFabric;
import kong.unirest.HttpRequest;
import kong.unirest.MultipartBody;
import kong.unirest.UnirestInstance;

import java.net.URL;
import java.util.List;

public class PostCollectionLoader extends CollectionLoader {
    public PostCollectionLoader(UnirestInstance client, TaskFabric taskFabric, HtmlBuilder<?> builder, String selector) {
        super(client, taskFabric, builder, selector);
    }

    public PostCollectionLoader(UnirestInstance client, HtmlBuilder<?> builder, String selector) {
        super(client, builder, selector);
    }

    public PostCollectionLoader(HtmlBuilder<?> builder, String selector) {
        super(builder, selector);
    }

    @Override
    protected HttpRequest<?> requestBuilder(UnirestInstance client, URL url, List<Pair<String, String>> fields) {
        MultipartBody ret = client.post(url.toString()).multiPartContent();
        if (fields != null) {
            fields.forEach(pair -> ret.field(pair.getKey(), pair.getValue()));
        }
        return ret;
    }
}
