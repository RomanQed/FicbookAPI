package com.github.romanqed.api.html;

import com.github.romanqed.api.util.Checks;
import com.github.romanqed.api.util.Urls;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.URL;

public class Tag extends AbstractHtmlBased {
    public static final AbstractHtmlBuilder<Tag> BUILDER = new TagBuilder();
    private String title;
    private String description;

    public Tag(URL url) {
        this.url = Checks.requireCorrectValue(url, Tag::validateUrl);
        title = "";
        description = "";
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

    public static class TagBuilder extends AbstractHtmlBuilder<Tag> {
        @Override
        public Tag build(URL url, Document page) {
            Tag ret = new Tag(url);
            String rawTitle = page.selectFirst("h1").text();
            ret.title = rawTitle.substring(rawTitle.indexOf('«') + 1, rawTitle.indexOf('»'));
            ret.description = page.selectFirst("div.well").text();
            return ret;
        }

        @Override
        public Tag build(Element node) {
            Tag ret = new Tag(Urls.parseFicbookUrl(node.attr("href")));
            ret.title = node.text();
            ret.description = node.attr("title").replaceAll("<.{1,2}>", "");
            return ret;
        }
    }
}
