package com.github.romanqed.api;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.URL;

public class Chapter extends AbstractHtmlBased {
    public Chapter(Element htmlElement) {
        // TODO
    }

    public Chapter(int parent, int currentId) {

    }

    protected Chapter(URL checkedLink) {
        link = checkedLink;
    }

    @Override
    protected void fromPage(Document document) {

    }

    @Override
    public boolean fullLoaded() {
        return false;
    }
}
