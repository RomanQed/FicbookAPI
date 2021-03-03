package com.github.romanqed.api.exchangers;

import com.github.romanqed.api.loaders.AbstractLoader;
import com.github.romanqed.api.util.Pair;
import kong.unirest.HttpRequest;
import kong.unirest.UnirestInstance;

import java.net.URL;
import java.util.List;

public abstract class AbstractExchanger extends AbstractLoader {
    public AbstractExchanger(UnirestInstance client) {
        super(client);
    }

    @Override
    protected HttpRequest<?> requestBuilder(UnirestInstance client, URL url, List<Pair<String, String>> fields) {
        // TODO
        return null;
    }
}
