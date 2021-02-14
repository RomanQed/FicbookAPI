package com.github.romanqed.api.html.builders;

import com.github.romanqed.api.html.entities.Chapter;
import com.github.romanqed.api.interfaces.HtmlPageBuilder;
import com.github.romanqed.api.util.Checks;
import com.github.romanqed.api.util.ParseUtil;
import com.github.romanqed.api.util.Urls;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.URL;

public class ChapterBuilder implements HtmlPageBuilder<Chapter> {
    @Override
    public Chapter build(URL url, Document page) {
        Chapter ret = new Chapter(Urls.sliceUrlLastPath(url));
        Element body = page.selectFirst("article.article");
        Element title = body.selectFirst("div.title-area");
        ret.setTitle(Checks.requireNonExcept(() -> title.selectFirst("h2").text(), ""));
        ret.setDate(ParseUtil.parseNativeDate(title.selectFirst("div.part-date span").attr("title")));
        ret.setBody(Checks.requireNonExcept(() -> body.selectFirst("div[id=content]").wholeText(), ""));
        ret.setNotes(Checks.requireNonExcept(
                () -> body.selectFirst("div.part-comment-bottom div").wholeText(),
                ""
        ));
        return ret;
    }

    @Override
    public Chapter build(Element node) {
        Element a = node.selectFirst("a");
        Chapter ret = new Chapter(
                ParseUtil.sliceLastPath(a.attr("href").replaceAll("#.*", ""))
        );
        ret.setTitle(a.text());
        ret.setDate(ParseUtil.parseNativeDate(Checks.requireNonExcept(
                () -> node.selectFirst("div span").attr("title"),
                ""
        )));
        return ret;
    }
}