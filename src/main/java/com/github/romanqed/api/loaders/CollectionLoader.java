package com.github.romanqed.api.loaders;

import com.github.romanqed.api.interfaces.EntityCollection;
import com.github.romanqed.api.interfaces.EntityCollectionBuilder;
import com.github.romanqed.api.interfaces.lambdas.ResponseProcessor;
import com.github.romanqed.api.util.Pair;
import com.github.romanqed.concurrent.Task;
import com.github.romanqed.concurrent.TaskFabric;
import kong.unirest.UnirestInstance;
import org.jsoup.Jsoup;

import java.net.URL;
import java.util.Collections;
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

    public <T, E extends EntityCollection<T>> Task<E>
    asyncLoad(URL url, List<Pair<String, String>> fields, EntityCollectionBuilder<T, E> builder) {
        ResponseProcessor<E> processor = (u, b) -> builder.build(Jsoup.parse(b));
        return super.asyncLoad(url, fields, processor);
    }

    public <T, E extends EntityCollection<T>> Task<E> asyncLoad(URL url, EntityCollectionBuilder<T, E> builder) {
        return asyncLoad(url, null, builder);
    }

    public <T> Task<EntityCollection<T>>
    asyncLoad(URL url, int page, EntityCollectionBuilder<T, EntityCollection<T>> builder) {
        return asyncLoad(url, Collections.singletonList(new Pair<>("p", Integer.toString(page))), builder);
    }
}
