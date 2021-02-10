package com.github.romanqed.api.html;

import kong.unirest.HttpRequest;
import kong.unirest.MultipartBody;
import kong.unirest.UnirestInstance;

import java.net.URL;
import java.util.Map;

public class PostCollectionLoader extends CollectionLoader {
    @Override
    protected HttpRequest<?> requestBuilder(UnirestInstance client, URL url, Map<String, String> fields) {
        MultipartBody ret = client.post(url.toString()).multiPartContent();
        if (fields != null) {
            fields.forEach(ret::field);
        }
        return ret;
    }
}
