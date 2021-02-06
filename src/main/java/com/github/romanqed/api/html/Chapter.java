package com.github.romanqed.api.html;

import com.github.romanqed.api.util.Checks;
import com.github.romanqed.api.util.ParseUtil;
import com.github.romanqed.api.util.Urls;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.URL;
import java.util.Date;

public class Chapter extends AbstractHtmlBased {
    private String title;
    private Date date;
    private String body = "";
    private String notes = "";

    public Chapter(Element htmlElement) {
        Element a = htmlElement.selectFirst("a");
        url = Urls.parseAndValidateUrl(
                a.attr("href").replaceAll("#.*", ""),
                Chapter::validateUrl
        );
        title = a.text();
        date = ParseUtil.parseNativeDate(Checks.requireNonExcept(
                () -> htmlElement.selectFirst("div span").attr("title"),
                ""
        ));
    }

    protected Chapter(URL url) {
        this.url = Checks.requireCorrectValue(url, Chapter::validateUrl);
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
    protected void fromPage(String rawPage) {
        Document page = Jsoup.parse(rawPage);
        Element body = page.selectFirst("article.article");
        Element title = body.selectFirst("div.title-area");
        this.title = Checks.requireNonExcept(() -> title.selectFirst("h2").text(), "");
        this.date = ParseUtil.parseNativeDate(title.selectFirst("div.part-date span").attr("title"));
        this.body = Checks.requireNonExcept(() -> body.selectFirst("div[id=content]").wholeText(), "");
        this.notes = Checks.requireNonExcept(() -> body.selectFirst("div.part-comment-bottom div").wholeText(), "");
    }

    @Override
    public String toString() {
        return "[Chapter] " + title + " " + super.toString();
    }
}
