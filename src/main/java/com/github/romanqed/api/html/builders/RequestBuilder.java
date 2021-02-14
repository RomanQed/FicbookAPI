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
import java.util.Set;

public class RequestBuilder implements HtmlPageBuilder<Request> {
    @Override
    public Request build(URL url, Document page) {
        Request ret = new Request(Urls.sliceUrlLastPath(url));
        ret.setPremium(page.selectFirst("div.premium-notice") != null);
        Element content = page.selectFirst("section.request-content");
        ret.setTitle(content.selectFirst("h1.mb-0").text());
        ret.setAuthor(User.BUILDER.build(content.selectFirst("a.avatar-nickname")));
        Elements counts = content.select("span.int-count");
        ret.setLikes(ParseUtil.parseInt(counts.get(0).text()));
        ret.setWorks(ParseUtil.parseInt(counts.get(1).text()));
        ret.setBookmarks(ParseUtil.parseInt(counts.get(2).text()));
        String prefix = "div section div";
        ParseUtil.parseHtmlNodes(Fandom.BUILDER, content.select(prefix + "[class!=tags] a"), ret.getFandoms());
        Elements spans = content.select(prefix + ":has(span.help)");
        Set<Direction> retDirections = ret.getDirections();
        spans.get(0).select("span.help").forEach(e -> {
            retDirections.add(Direction.fromName(ParseUtil.safetyText(e.text())));
        });
        Set<Rating> retRatings = ret.getRatings();
        spans.get(1).select("span.help").forEach(e -> {
            retRatings.add(Rating.fromName(ParseUtil.safetyText(e.text())));
        });
        ParseUtil.parseHtmlNodes(Tag.BUILDER, content.select("a.tag"), ret.getTags());
        Element characters = content.selectFirst(prefix + ":contains(Персонажи)");
        if (characters != null) {
            Collections.addAll(ret.getCharacters(), characters.text().split(","));
        }
        ret.setDescription(content.selectFirst("div.word-break").wholeText());
        ret.setHiddenDescription(Checks.requireNonExcept(
                () -> content.selectFirst("div.word-break[style]").wholeText(),
                ""
        ));
        ret.setCreationDate(ParseUtil.parseNativeDate(content.selectFirst("p span").attr("title")));
        return ret;
    }

    @Override
    public Request build(Element node) {
        Element title = node.selectFirst("a.visit-link");
        Request ret = new Request(ParseUtil.sliceLastPath(title.attr("href")));
        ret.setTitle(title.text());
        ret.setLikes(ParseUtil.parseInt(node.selectFirst("span.request-likes-counter").text()));
        ret.setBookmarks(ParseUtil.parseInt(node.selectFirst("span.request-bookmark-counter").text()));
        ret.setWorks(ParseUtil.parseInt(node.selectFirst("span.container-counter").text()));
        ParseUtil.parseHtmlNodes(Fandom.BUILDER, node.select("strong.title a"), ret.getFandoms());
        Elements info = node.select("section.request-description div");
        Set<Direction> retDirections = ret.getDirections();
        info.get(0).select("span.help").forEach(e -> {
            retDirections.add(Direction.fromName(ParseUtil.safetyText(e.text())));
        });
        Set<Rating> retRatings = ret.getRatings();
        info.get(1).select("span.help").forEach(e -> {
            retRatings.add(Rating.fromName(ParseUtil.safetyText(e.text())));
        });
        ParseUtil.parseHtmlNodes(Tag.BUILDER, node.select("div.tags a"), ret.getTags());
        ret.setDescription(node.selectFirst("div.post-content").text());
        return ret;
    }
}
