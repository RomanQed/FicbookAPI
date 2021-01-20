package com.github.romanqed.api;

import com.github.romanqed.api.urls.Urls;
import org.jsoup.nodes.Element;

import java.net.URL;

public class Tag extends AbstractDefHtmlBased {
    private String title = "";
    private String description = "";

    protected Tag(URL checkedLink) {
        link = checkedLink;
    }

    public Tag(Element htmlElement) throws Exception {
        fromHtml(htmlElement);
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean validateHtml(Element element) {
        return super.validateHtml(element) && validateLocalUrl(element.attr("href"));
    }

    @Override
    public void fromHtml(Element element) throws Exception {
        super.fromHtml(element);
        link = Urls.parseUrl(element.attr("href"));
        title = element.text();
        description = element.attr("title").replaceAll("<.{1,2}>", "");
    }

    @Override
    public boolean fullLoaded() {
        return !(title.isEmpty() || description.isEmpty());
    }

    @Override
    public boolean validateUrl(URL url) {
        return Urls.validateChildUrl(Urls.TAGS, url);
    }

    @Override
    public String toString() {
        return "[Tag] " + title + " " + super.toString();
    }
}
