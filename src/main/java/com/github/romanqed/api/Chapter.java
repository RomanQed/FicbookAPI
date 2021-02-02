package com.github.romanqed.api;

import com.github.romanqed.api.html.AbstractHtmlBased;
import org.jsoup.nodes.Element;

import java.net.URL;

public class Chapter extends AbstractHtmlBased {
    private final int id = 0;
    private String title;
    private String date;
    private String body;
    private String notes;

    public Chapter(Element htmlElement) {
        // TODO
    }

    protected Chapter(URL link) {

    }

    @Override
    protected void fromPage(String rawPage) {
        // TODO
    }
}
