package com.github.romanqed.api;

import org.jsoup.nodes.Element;

import java.net.URL;

public class Chapter extends AbstractHtmlBased {
    // TODO
    private final int id = 0;

    public Chapter(Element htmlElement) {
        // TODO
    }

    public Chapter(int parent, int id) {

    }

    protected Chapter(URL checkedLink) {
        link = checkedLink;
    }

    @Override
    protected void fromPage(String rawDocument) {

    }

    @Override
    public boolean fullLoaded() {
        return false;
    }
}
