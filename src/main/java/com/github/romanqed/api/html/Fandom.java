package com.github.romanqed.api.html;

import com.github.romanqed.api.util.Urls;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.URL;

public class Fandom extends AbstractHtmlBased {
    private String title = "";

    protected Fandom(URL checkedLink) {
        link = checkedLink;
    }

    public Fandom(String name) {
        // TODO
    }

    public Fandom(Element htmlElement) {
        link = Urls.parseAndValidateUrl(htmlElement.attr("href"), this::validateUrl);
        title = htmlElement.text().trim();
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean fullLoaded() {
        return !title.isEmpty();
    }

    @Override
    public boolean validateUrl(URL url) {
        return Urls.validateChildUrl(Urls.FANDOMS, url);
    }

    @Override
    public String toString() {
        return "[Fandom] " + title + " " + super.toString();
    }

    @Override
    protected void fromPage(Document document) {
        // TODO
    }
}
