package com.github.romanqed.api;

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
        link = Urls.parseAndValidateUrl(htmlElement.attr("href"), Fandom::validateUrl);
        title = htmlElement.text().trim();
    }

    public static boolean validateUrl(URL url) {
        return Urls.validateChildUrl(Urls.FANDOMS, url);
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean fullLoaded() {
        return !title.isEmpty();
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
