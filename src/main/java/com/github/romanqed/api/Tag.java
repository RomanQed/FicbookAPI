package com.github.romanqed.api;

import com.github.romanqed.api.util.Urls;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.URL;

public class Tag extends AbstractHtmlBased {
    private String title = "";
    private String description = "";

    protected Tag(URL checkedLink) {
        link = checkedLink;
    }

    public Tag(int id) {
        // TODO
    }

    public Tag(Element htmlElement) {
        link = Urls.parseAndValidateUrl(htmlElement.attr("href"), Tag::validateUrl);
        title = htmlElement.text();
        description = htmlElement.attr("title").replaceAll("<.{1,2}>", "");
    }

    public static boolean validateUrl(URL url) {
        return Urls.validateChildUrl(Urls.TAGS, url);
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean fullLoaded() {
        return !(title.isEmpty() || description.isEmpty());
    }


    @Override
    public String toString() {
        return "[Tag] " + title + " " + super.toString();
    }

    @Override
    protected void fromPage(Document document) {
        // TODO
    }
}
