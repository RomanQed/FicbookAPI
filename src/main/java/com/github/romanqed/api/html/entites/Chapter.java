package com.github.romanqed.api.html.entites;

import com.github.romanqed.api.AbstractLinkable;
import com.github.romanqed.api.interfaces.HtmlPageBuilder;
import com.github.romanqed.api.util.Checks;
import com.github.romanqed.api.util.ParseUtil;
import com.github.romanqed.api.util.Urls;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.URL;
import java.util.Date;

public class Chapter extends AbstractLinkable {
    public static final HtmlPageBuilder<Chapter> BUILDER = new ChapterBuilder();
    private String title;
    private Date date;
    private String body;
    private String notes;

    public Chapter(URL url) {
        this.url = Checks.requireCorrectValue(url, Chapter::validateUrl);
        title = "";
        date = new Date();
        body = "";
        notes = "";
    }

    public static boolean validateUrl(URL url) {
        return Urls.validateChildUrl(Urls.FANFIC, url);
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getNotes() {
        return notes;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "[Chapter] " + title + " " + super.toString();
    }

    public static class ChapterBuilder implements HtmlPageBuilder<Chapter> {
        @Override
        public Chapter build(URL url, Document page) {
            Chapter ret = new Chapter(url);
            Element body = page.selectFirst("article.article");
            Element title = body.selectFirst("div.title-area");
            ret.title = Checks.requireNonExcept(() -> title.selectFirst("h2").text(), "");
            ret.date = ParseUtil.parseNativeDate(title.selectFirst("div.part-date span").attr("title"));
            ret.body = Checks.requireNonExcept(() -> body.selectFirst("div[id=content]").wholeText(), "");
            ret.notes = Checks.requireNonExcept(
                    () -> body.selectFirst("div.part-comment-bottom div").wholeText(),
                    ""
            );
            return ret;
        }

        @Override
        public Chapter build(Element node) {
            Element a = node.selectFirst("a");
            Chapter ret = new Chapter(
                    Urls.parseFicbookUrl(a.attr("href").replaceAll("#.*", ""))
            );
            ret.title = a.text();
            ret.date = ParseUtil.parseNativeDate(Checks.requireNonExcept(
                    () -> node.selectFirst("div span").attr("title"),
                    ""
            ));
            return ret;
        }
    }
}
