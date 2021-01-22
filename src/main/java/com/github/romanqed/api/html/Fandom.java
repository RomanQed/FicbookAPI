package com.github.romanqed.api.html;

import com.github.romanqed.api.util.Urls;
import org.jsoup.nodes.Element;

import java.net.URL;

public class Fandom extends AbstractHtmlBased {
    private String title = "";

    protected Fandom(URL checkedLink) {
        link = checkedLink;
    }

    public Fandom(Element htmlElement) {
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
    public void fromHtml(Element element) {
        link = Urls.parseAndValidateUrl(element.attr("href"), this::validateUrl);
        title = element.text().trim();
    }

    @Override
    public String toString() {
        return "[Fandom] " + title + " " + super.toString();
    }
}
