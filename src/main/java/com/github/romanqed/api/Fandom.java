package com.github.romanqed.api;

import com.github.romanqed.api.urls.Urls;
import org.jsoup.nodes.Element;

import java.net.URL;

public class Fandom extends AbstractDefHtmlBased {
    private String title = "";

    protected Fandom(URL checkedLink) {
        link = checkedLink;
    }

    public Fandom(Element htmlElement) throws Exception {
        fromHtml(htmlElement);
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
    public void fromHtml(Element element) throws Exception {
        super.fromHtml(element);
        link = Urls.parseUrl(element.attr("href"));
        title = element.text().trim();
    }

    @Override
    public boolean validateHtml(Element element) {
        return super.validateHtml(element) && validateLocalUrl(element.attr("href"));
    }

    @Override
    public String toString() {
        return "[Fandom] " + title + " " + super.toString();
    }
}
