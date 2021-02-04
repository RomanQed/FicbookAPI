package com.github.romanqed.api;

import com.github.romanqed.api.interfaces.Linkable;

import java.net.URL;

public abstract class AbstractLinkable implements Linkable {
    protected URL url;

    @Override
    public URL getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "[URL] " + url;
    }
}
