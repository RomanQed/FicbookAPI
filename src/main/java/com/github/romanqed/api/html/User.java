package com.github.romanqed.api.html;

import org.jsoup.nodes.Document;

import java.net.URL;

public class User extends AbstractHtmlBased {

    @Override
    public boolean fullLoaded() {
        return false;
    }

    @Override
    public boolean validateUrl(URL url) {
        return false;
    }

    @Override
    protected void fromPage(Document document) {
        // TODO
    }
}
