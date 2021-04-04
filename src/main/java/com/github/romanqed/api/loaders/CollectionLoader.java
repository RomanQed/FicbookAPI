package com.github.romanqed.api.loaders;

import com.github.romanqed.api.interfaces.CollectionBuilder;
import com.github.romanqed.api.interfaces.lambdas.ResponseProcessor;
import com.github.romanqed.api.util.Pair;
import com.github.romanqed.concurrent.Task;
import com.github.romanqed.concurrent.TaskFabric;
import kong.unirest.UnirestInstance;
import org.jsoup.Jsoup;

import java.net.URL;
import java.util.List;

public class CollectionLoader extends AsyncLoader {
    public CollectionLoader(UnirestInstance client, TaskFabric fabric) {
        super(client, fabric);
    }

    public CollectionLoader(UnirestInstance client) {
        this(client, null);
    }

    public CollectionLoader() {
        this(null, null);
    }

    public <T> Task<Pair<Integer, List<T>>>
    asyncLoad(URL url, List<Pair<String, String>> fields, CollectionBuilder<T> builder) {
        ResponseProcessor<Pair<Integer, List<T>>> processor = (u, b) -> builder.build(Jsoup.parse(b));
        return asyncLoad(url, fields, processor);
    }
}
