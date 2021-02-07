package com.github.romanqed.api.html;

import com.github.romanqed.api.util.Checks;
import com.github.romanqed.api.util.Urls;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.URL;

public class Tag extends AbstractHtmlBased {
    private String title = "";
    private String description = "";

    public Tag(URL url) {
        this.url = Checks.requireCorrectValue(url, Tag::validateUrl);
    }

    public Tag(Element htmlElement) {
        url = Urls.parseAndValidateUrl(htmlElement.attr("href"), Tag::validateUrl);
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
    public String toString() {
        return "[Tag] " + title + " " + super.toString();
    }

    @Override
    protected void fromPage(Document page) {
        String rawTitle = page.selectFirst("h1").text();
        title = rawTitle.substring(rawTitle.indexOf('«') + 1, rawTitle.indexOf('»'));
        description = page.selectFirst("div.well").text();
    }
}
