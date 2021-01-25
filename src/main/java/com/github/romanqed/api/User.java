package com.github.romanqed.api;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.URL;

public class User extends AbstractHtmlBased {
    public User(Element htmlElement) {
        // TODO
    }

    public static boolean validateUrl(URL url) {
        return false;
    }

    @Override
    public boolean fullLoaded() {
        return false;
    }

    @Override
    protected void fromPage(Document document) {
        // TODO
    }
}
