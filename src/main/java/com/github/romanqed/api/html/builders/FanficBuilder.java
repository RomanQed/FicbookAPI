package com.github.romanqed.api.html.builders;

import com.github.romanqed.api.enums.*;
import com.github.romanqed.api.html.entities.*;
import com.github.romanqed.api.interfaces.HtmlPageBuilder;
import com.github.romanqed.api.json.Reward;
import com.github.romanqed.api.util.Checks;
import com.github.romanqed.api.util.ParseUtil;
import com.github.romanqed.api.util.Urls;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;

public class FanficBuilder implements HtmlPageBuilder<Fanfic> {
    @Override
    public Fanfic build(URL url, Document page) {
        Fanfic ret = new Fanfic(Urls.sliceUrlLastPath(url));
        Element mainInfo = page.selectFirst("div.fanfic-main-info");
        ret.title = mainInfo.selectFirst("h1").text();
        ParseUtil.parseHtmlNodes(Fandom.BUILDER, mainInfo.select("div.mb-10 a"), ret.fandoms);
        ret.direction = Direction.fromName(
                ParseUtil.safetyText(mainInfo.selectFirst("div.direction svg").attr("class"))
        );
        ret.rating = Rating.fromName(ParseUtil.safetyText(mainInfo.selectFirst("strong span").text()));
        ret.isTranslate = mainInfo.selectFirst("span.badge-translate") != null;
        ret.status = Status.fromName(
                ParseUtil.safetyText(mainInfo.selectFirst("span.badge-secondary svg").attr("class"))
        );
        ret.likes = ParseUtil.safetyNumber(mainInfo.selectFirst("span.badge-like").text());
        ret.inCollections = ParseUtil.safetyNumber(page.selectFirst("span.main-info:has(svg.ic_bookmark)").text());
        Element hat = page.selectFirst("section.fanfic-hat");
        ret.isPremium = hat.selectFirst("div.fanfic-hat-premium-notice") != null;
        if (ret.isTranslate) {
            Element info = hat.selectFirst("div.mb-15");
            ret.originalAuthor = info.selectFirst("a").text();
            ret.originalFanfic = info.selectFirst("div.urlize").text();
        }
        Element rewardElements = hat.selectFirst("fanfic-reward-list");
        if (rewardElements != null) {
            JsonElement rewards = JsonParser.parseString(rewardElements.attr(":initial-fic-rewards-list"));
            if (rewards != null && rewards.isJsonArray()) {
                rewards.getAsJsonArray().forEach(reward -> ret.rewards.add(new Reward(reward.getAsJsonObject())));
            }
        }
        Elements authors = hat.select("div.hat-creator-container div.creator-info");
        for (Element author : authors) {
            ret.authors.put(
                    User.BUILDER.build(author.selectFirst("a")),
                    AuthorRole.fromName(author.selectFirst("i").text())
            );
        }
        ParseUtil.parseHtmlNodes(Pairing.BUILDER, hat.select("a.pairing-link"), ret.pairings);
        Element size = hat.selectFirst("div.mb-5 div span");
        ret.size = Size.fromName(ParseUtil.safetyText(size.text()));
        ret.actualPages = ParseUtil.parseMixedNum(size.parent().text().split(",")[1]);
        ParseUtil.parseHtmlNodes(Tag.BUILDER, hat.select("div.tags a"), ret.tags);
        ret.description = hat.selectFirst("div[itemprop=description]").text();
        ret.dedication = Checks.requireNonExcept(
                () -> hat.selectFirst("div.mb-5:contains(Посвящение)").wholeText(),
                ""
        );
        ret.notes = Checks.requireNonExcept(
                () -> hat.selectFirst("div.mb-5:contains(Примечания)").wholeText(),
                ""
        );
        ret.copyright = hat.select("div.mb-5 div").last().text();
        ParseUtil.parseHtmlNodes(Chapter.BUILDER, page.select("article li.part"), ret.chapters);
        if (ret.chapters.isEmpty()) {
            ret.chapters.add(new Chapter(Urls.sliceUrlLastPath(url)));
        }
        Element cover = hat.selectFirst("fanfic-cover");
        if (cover != null) {
            ret.cover = Urls.parseUrl(cover.attr("src-original"));
        }
        return ret;
    }

    @Override
    public Fanfic build(Element node) {
        Element title = node.selectFirst("h3.fanfic-inline-title a");
        Fanfic ret = new Fanfic(ParseUtil.sliceLastPath(title.attr("href").split("\\?")[0]));
        ret.title = title.text();
        ret.isPremium = node.attr("class").contains("fanfic-inline-hot");
        String direction = node.selectFirst("div.direction svg").attr("class");
        ret.direction = Direction.fromName(ParseUtil.safetyText(direction));
        ret.isTranslate = node.selectFirst("span.badge-translate") != null;
        String rating = node.selectFirst("strong span").text();
        ret.rating = Rating.fromName(ParseUtil.safetyText(rating));
        String status = node.selectFirst("span.badge-secondary svg").attr("class");
        ret.status = Status.fromName(ParseUtil.safetyText(status));
        ret.likes = ParseUtil.safetyNumber(node.selectFirst("span.badge-like").text());
        Elements authors = node.select("span.author a");
        if (ret.isTranslate) {
            ret.authors.put(User.BUILDER.build(authors.first()), AuthorRole.TRANSLATOR);
            ret.originalAuthor = authors.last().text();
        } else {
            ret.authors.put(User.BUILDER.build(authors.first()), AuthorRole.AUTHOR);
        }
        String prefix = "dl.fanfic-inline-info";
        Element fandoms = node.select(prefix).get(1);
        ParseUtil.parseHtmlNodes(Fandom.BUILDER, fandoms.select("a"), ret.fandoms);
        Elements pairings = node.select(prefix + " a.pairing-link");
        ParseUtil.parseHtmlNodes(Pairing.BUILDER, pairings, ret.pairings);
        Element size = node.selectFirst(prefix + " span.size-title");
        ret.size = Size.fromName(ParseUtil.safetyText(size.text()));
        ret.actualPages = ParseUtil.safetyNumber(size.parent().text().split(",")[1]);
        Elements tags = node.select(prefix + " a.tag");
        ParseUtil.parseHtmlNodes(Tag.BUILDER, tags, ret.tags);
        ret.description = node.selectFirst("div.fanfic-description").wholeText();
        Element cover = node.selectFirst("picture.fanfic-hat-cover-picture img");
        if (cover != null) {
            ret.cover = Urls.parseUrl(cover.attr("src"));
        }
        return ret;
    }
}
