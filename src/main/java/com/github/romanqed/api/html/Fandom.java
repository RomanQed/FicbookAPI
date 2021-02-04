package com.github.romanqed.api.html;

import com.github.romanqed.api.util.Checks;
import com.github.romanqed.api.util.Urls;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.URL;

public class Fandom extends AbstractHtmlBased {
    private String title = "";
    private int pages = -1;

    public Fandom(URL link) {
        this.url = Checks.requireCorrectValue(link, Fandom::validateUrl);
    }

    public Fandom(Element htmlElement) {
        url = Urls.parseAndValidateUrl(htmlElement.attr("href"), Fandom::validateUrl);
        title = htmlElement.text().trim();
    }

    public static boolean validateUrl(URL url) {
        return Urls.validateChildUrl(Urls.FANDOMS, url);
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "[Fandom] " + title + " [Pages] " + pages + " " + super.toString();
    }

    @Override
    protected void fromPage(String rawPage) {
        Document page = Jsoup.parse(rawPage);
        String rawTitle = page.selectFirst("h1").text();
        title = rawTitle.substring(rawTitle.indexOf('«') + 1, rawTitle.indexOf('»'));
        Element pages = page.select("div.paging-description b").last();
        this.pages = Checks.requireNonExcept(() -> Integer.parseInt(pages.text()), 1);
    }
}
