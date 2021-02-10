package com.github.romanqed.api.html.entites;

import com.github.romanqed.api.AbstractLinkable;
import com.github.romanqed.api.interfaces.HtmlPageBuilder;
import com.github.romanqed.api.util.Checks;
import com.github.romanqed.api.util.Urls;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.URL;

public class Fandom extends AbstractLinkable {
    public static final HtmlPageBuilder<Fandom> BUILDER = new FandomBuilder();
    private String title;

    public Fandom(URL url) {
        this.url = Checks.requireCorrectValue(url, Fandom::validateUrl);
        title = "";
    }

    public static boolean validateUrl(URL url) {
        return Urls.validateChildUrl(Urls.FANDOMS, url);
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "[Fandom] " + title + " " + super.toString();
    }

    public static class FandomBuilder implements HtmlPageBuilder<Fandom> {
        @Override
        public Fandom build(URL url, Document page) {
            Fandom ret = new Fandom(url);
            String rawTitle = page.selectFirst("h1").text();
            ret.title = rawTitle.substring(rawTitle.indexOf('«') + 1, rawTitle.indexOf('»'));
            return ret;
        }

        @Override
        public Fandom build(Element node) {
            Fandom ret = new Fandom(Urls.parseFicbookUrl(node.attr("href")));
            ret.title = node.text().trim();
            return ret;
        }
    }
}
