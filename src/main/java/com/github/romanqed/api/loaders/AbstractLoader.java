package com.github.romanqed.api.loaders;

import com.github.romanqed.api.interfaces.Loader;
import com.github.romanqed.api.util.Checks;
import com.github.romanqed.api.util.Pair;
import kong.unirest.*;

import java.io.IOException;
import java.net.URL;
import java.util.List;


public abstract class AbstractLoader implements Loader {
    protected UnirestInstance client;

    public AbstractLoader(UnirestInstance client) {
        this.client = Checks.requireNonNullElse(client, Unirest.spawnInstance());
    }

    @Override
    public String load(URL url, List<Pair<String, String>> fields) throws IOException {
        HttpResponse<String> response = requestBuilder(client, url, fields).asString();
        if (!response.isSuccess()) {
            throw new IOException("Server returned HTTP response code: " + response.getStatus());
        }
        return response.getBody();
    }

    protected HttpRequest<?> requestBuilder(UnirestInstance client, URL url, List<Pair<String, String>> fields) {
        GetRequest ret = client.get(url.toString());
        if (fields != null) {
            fields.forEach(pair -> ret.queryString(pair.getKey(), pair.getValue()));
        }
        return ret;
    }
}
