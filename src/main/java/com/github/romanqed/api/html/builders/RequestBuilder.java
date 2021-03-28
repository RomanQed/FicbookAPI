package com.github.romanqed.api.html.builders;

import com.github.romanqed.api.enums.Direction;
import com.github.romanqed.api.enums.Rating;
import com.github.romanqed.api.html.entities.Fandom;
import com.github.romanqed.api.html.entities.Request;
import com.github.romanqed.api.html.entities.Tag;
import com.github.romanqed.api.html.entities.User;
import com.github.romanqed.api.interfaces.HtmlPageBuilder;
import com.github.romanqed.api.util.Checks;
import com.github.romanqed.api.util.ParseUtil;
import com.github.romanqed.api.util.Urls;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.Collections;

public class RequestBuilder implements HtmlPageBuilder<Request> {
    @Override
    public Request build(URL url, Document page) {
        Request ret = new Request(Urls.sliceUrlLastPath(url));
        ret.isPremium = page.selectFirst("div.premium-notice") != null;
        Element content = page.selectFirst("section.request-content");
        ret.title = content.selectFirst("h1.mb-0").text();
        ret.author = User.BUILDER.build(content.selectFirst("a.avatar-nickname"));
        Elements counts = content.select("span.int-count");
        ret.likes = ParseUtil.parseInt(counts.get(0).text());
        ret.works = ParseUtil.parseInt(counts.get(1).text());
        ret.bookmarks = ParseUtil.parseInt(counts.get(2).text());
        String prefix = "div section div";
        ParseUtil.parseHtmlNodes(Fandom.BUILDER, content.select(prefix + "[class!=tags] a"), ret.fandoms);
        Elements spans = content.select(prefix + ":has(span.help)");
        spans.get(0).select("span.help").forEach(e -> {
            ret.directions.add(Direction.fromName(ParseUtil.safetyText(e.text())));
        });
        spans.get(1).select("span.help").forEach(e -> {
            ret.ratings.add(Rating.fromName(ParseUtil.safetyText(e.text())));
        });
        ParseUtil.parseHtmlNodes(Tag.BUILDER, content.select("a.tag"), ret.tags);
        Element characters = content.selectFirst(prefix + ":contains(Персонажи)");
        if (characters != null) {
            Collections.addAll(ret.characters, characters.text().split(","));
        }
        ret.description = content.selectFirst("div.word-break").wholeText();
        ret.hiddenDescription = Checks.requireNonExcept(
                () -> content.selectFirst("div.word-break[style]").wholeText(),
                ""
        );
        ret.creationDate = ParseUtil.parseNativeDate(content.selectFirst("p span").attr("title"));
        return ret;
    }

    @Override
    public Request build(Element node) {
        Element title = node.selectFirst("a.visit-link");
        Request ret = new Request(ParseUtil.sliceLastPath(title.attr("href")));
        ret.title = title.text();
        ret.likes = ParseUtil.parseInt(node.selectFirst("span.request-likes-counter").text());
        ret.bookmarks = ParseUtil.parseInt(node.selectFirst("span.request-bookmark-counter").text());
        ret.works = ParseUtil.parseInt(node.selectFirst("span.container-counter").text());
        ParseUtil.parseHtmlNodes(Fandom.BUILDER, node.select("strong.title a"), ret.fandoms);
        Elements info = node.select("section.request-description div");
        info.get(0).select("span.help").forEach(e -> {
            ret.directions.add(Direction.fromName(ParseUtil.safetyText(e.text())));
        });
        info.get(1).select("span.help").forEach(e -> {
            ret.ratings.add(Rating.fromName(ParseUtil.safetyText(e.text())));
        });
        ParseUtil.parseHtmlNodes(Tag.BUILDER, node.select("div.tags a"), ret.tags);
        ret.description = node.selectFirst("div.post-content").text();
        return ret;
    }
}
