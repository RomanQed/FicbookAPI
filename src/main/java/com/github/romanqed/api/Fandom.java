package com.github.romanqed.api;

import com.github.romanqed.api.util.Urls;
import org.jsoup.nodes.Element;

import java.net.URL;

public class Fandom extends AbstractHtmlBased {
    private String title = "";
    private int pages = -1;

    public Fandom(URL link) {
        if (!validateUrl(link)) {
            throw new IllegalArgumentException("Bad fandom url");
        }
        this.link = link;
    }

    public Fandom(String ref) {
        if (ref.isEmpty()) {
            throw new IllegalArgumentException("Bad fandom reference");
        }
        link = Urls.attachUrl(Urls.FANDOMS, ref);
    }
    
    public Fandom(String... splitRef) {
        this(String.join("/", splitRef));
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
        return !title.isEmpty() && pages != -1;
    }

    @Override
    public String toString() {
        return "[Fandom] " + title + " " + super.toString();
    }

    @Override
    protected void fromPage(String rawDocument) {
        // TODO
    }
}
