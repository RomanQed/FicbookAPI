package com.github.romanqed.api;

import com.github.romanqed.api.interfaces.Linkable;
import okhttp3.OkHttpClient;

import java.net.URL;

public abstract class AbstractLinkable implements Linkable {
    protected URL link;
    // FIXME
    public OkHttpClient client;

    @Override
    public URL getLink() {
        return link;
    }

    @Override
    public String toString() {
        return "[Link] " + link;
    }
}
