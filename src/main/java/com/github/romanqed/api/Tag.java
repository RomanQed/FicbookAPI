package com.github.romanqed.api;

import com.github.romanqed.api.util.Urls;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.URL;

public class Tag extends AbstractHtmlBased {
    private String title = "";
    private String description = "";

    public Tag(URL link) {
        if (!validateUrl(link)) {
            throw new IllegalArgumentException("Bad tag url");
        }
        this.link = link;
    }

    public Tag(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Bad tag id");
        }
        link = Urls.attachUrl(Urls.TAGS, Integer.toString(id));
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
    protected void fromPage(String rawPage) {
        Document page = Jsoup.parse(rawPage);
        String rawTitle = page.selectFirst("h1").text();
        title = rawTitle.substring(rawTitle.indexOf('«') + 1, rawTitle.indexOf('»'));
        description = page.selectFirst("div.well").text();
    }
}
