package com.github.romanqed.api.html;

import com.github.romanqed.api.util.Checks;
import com.github.romanqed.api.util.Urls;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.URL;

public class Fandom extends AbstractHtmlBased {
    public static final AbstractHtmlBuilder<Fandom> BUILDER = new FandomBuilder();
    private String title;
    private int pages;

    public Fandom(URL url) {
        this.url = Checks.requireCorrectValue(url, Fandom::validateUrl);
    }

    public Fandom() {
        title = "";
        pages = -1;
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

    public static class FandomBuilder extends AbstractHtmlBuilder<Fandom> {
        @Override
        public Fandom build(URL url, Document page) {
            Fandom ret = new Fandom(url);
            String rawTitle = page.selectFirst("h1").text();
            ret.title = rawTitle.substring(rawTitle.indexOf('«') + 1, rawTitle.indexOf('»'));
            Element pages = page.select("div.paging-description b").last();
            ret.pages = Checks.requireNonExcept(() -> Integer.parseInt(pages.text()), 1);
            return ret;
        }

        @Override
        public Fandom build(Element node) {
            Fandom ret = new Fandom();
            ret.url = Urls.parseAndValidateUrl(node.attr("href"), Fandom::validateUrl);
            ret.title = node.text().trim();
            return ret;
        }
    }
}
