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
import java.util.List;
import java.util.Map;

public class FanficBuilder implements HtmlPageBuilder<Fanfic> {
    @Override
    public Fanfic build(URL url, Document page) {
        Fanfic ret = new Fanfic(Urls.sliceUrlLastPath(url));
        Element mainInfo = page.selectFirst("div.fanfic-main-info");
        ret.setTitle(mainInfo.selectFirst("h1").text());
        ParseUtil.parseHtmlNodes(Fandom.BUILDER, mainInfo.select("div.mb-10 a"), ret.getFandoms());
        ret.setDirection(Direction.fromName(
                ParseUtil.safetyText(mainInfo.selectFirst("div.direction svg").attr("class"))
        ));
        ret.setRating(Rating.fromName(ParseUtil.safetyText(mainInfo.selectFirst("strong span").text())));
        ret.setTranslate(mainInfo.selectFirst("span.badge-translate") != null);
        ret.setStatus(Status.fromName(
                ParseUtil.safetyText(mainInfo.selectFirst("span.badge-secondary svg").attr("class"))
        ));
        ret.setLikes(ParseUtil.safetyNumber(mainInfo.selectFirst("span.badge-like").text()));
        ret.setInCollections(ParseUtil.safetyNumber(page.selectFirst("span.main-info:has(svg.ic_bookmark)").text()));
        Element hat = page.selectFirst("section.fanfic-hat");
        ret.setPremium(hat.selectFirst("div.fanfic-hat-premium-notice") != null);
        if (ret.isTranslate()) {
            Element info = hat.selectFirst("div.mb-15");
            ret.setOriginalAuthor(info.selectFirst("a").text());
            ret.setOriginalFanfic(info.selectFirst("div.urlize").text());
        }
        Element rewardElements = hat.selectFirst("fanfic-reward-list");
        List<Reward> retRewards = ret.getRewards();
        if (rewardElements != null) {
            JsonElement rewards = JsonParser.parseString(rewardElements.attr(":initial-fic-rewards-list"));
            if (rewards != null && rewards.isJsonArray()) {
                rewards.getAsJsonArray().forEach(reward -> retRewards.add(new Reward(reward.getAsJsonObject())));
            }
        }
        Elements authors = hat.select("div.hat-creator-container div.creator-info");
        Map<User, AuthorRole> retAuthors = ret.getAuthors();
        for (Element author : authors) {
            retAuthors.put(
                    User.BUILDER.build(author.selectFirst("a")),
                    AuthorRole.fromName(author.selectFirst("i").text())
            );
        }
        ParseUtil.parseHtmlNodes(Pairing.BUILDER, hat.select("a.pairing-link"), ret.getPairings());
        Element size = hat.selectFirst("div.mb-5 div span");
        ret.setSize(Size.fromName(ParseUtil.safetyText(size.text())));
        ret.setActualPages(ParseUtil.parseMixedNum(size.parent().text().split(",")[1]));
        ParseUtil.parseHtmlNodes(Tag.BUILDER, hat.select("div.tags a"), ret.getTags());
        ret.setDescription(hat.selectFirst("div[itemprop=description]").text());
        ret.setDedication(Checks.requireNonExcept(
                () -> hat.selectFirst("div.mb-5:contains(Посвящение)").wholeText(),
                ""
        ));
        ret.setNotes(Checks.requireNonExcept(
                () -> hat.selectFirst("div.mb-5:contains(Примечания)").wholeText(),
                ""
        ));
        ret.setCopyright(hat.select("div.mb-5 div").last().text());
        List<Chapter> retChapters = ret.getChapters();
        ParseUtil.parseHtmlNodes(Chapter.BUILDER, page.select("article li.part"), retChapters);
        if (retChapters.isEmpty()) {
            retChapters.add(new Chapter(Urls.sliceUrlLastPath(url)));
        }
        Element cover = hat.selectFirst("fanfic-cover");
        if (cover != null) {
            ret.setCover(Urls.parseUrl(cover.attr("src-original")));
        }
        return ret;
    }

    @Override
    public Fanfic build(Element node) {
        Element title = node.selectFirst("a.fanfic-inline-title");
        Fanfic ret = new Fanfic(ParseUtil.sliceLastPath(title.attr("href").split("\\?")[0]));
        ret.setTitle(title.text());
        ret.setPremium(node.attr("class").contains("fanfic-inline-hot"));
        String direction = node.selectFirst("div.direction svg").attr("class");
        ret.setDirection(Direction.fromName(ParseUtil.safetyText(direction)));
        ret.setTranslate(node.selectFirst("span.badge-translate") != null);
        String rating = node.selectFirst("strong span").text();
        ret.setRating(Rating.fromName(ParseUtil.safetyText(rating)));
        String status = node.selectFirst("span.badge-secondary svg").attr("class");
        ret.setStatus(Status.fromName(ParseUtil.safetyText(status)));
        ret.setLikes(ParseUtil.safetyNumber(node.selectFirst("span.badge-like").text()));
        Elements authors = node.select("span.author a");
        Map<User, AuthorRole> authorsMap = ret.getAuthors();
        if (ret.isTranslate()) {
            authorsMap.put(User.BUILDER.build(authors.first()), AuthorRole.TRANSLATOR);
            ret.setOriginalAuthor(authors.last().text());
        } else {
            authorsMap.put(User.BUILDER.build(authors.first()), AuthorRole.AUTHOR);
        }
        String prefix = "dl.fanfic-inline-info";
        Element fandoms = node.select(prefix).get(1);
        ParseUtil.parseHtmlNodes(Fandom.BUILDER, fandoms.select("a"), ret.getFandoms());
        Elements pairings = node.select(prefix + " a.pairing-link");
        ParseUtil.parseHtmlNodes(Pairing.BUILDER, pairings, ret.getPairings());
        Element size = node.selectFirst(prefix + " span.size-title");
        ret.setSize(Size.fromName(ParseUtil.safetyText(size.text())));
        ret.setActualPages(ParseUtil.safetyNumber(size.parent().text().split(",")[1]));
        Elements tags = node.select(prefix + " a.tag");
        ParseUtil.parseHtmlNodes(Tag.BUILDER, tags, ret.getTags());
        ret.setDescription(node.selectFirst("div.fanfic-description").wholeText());
        Element cover = node.selectFirst("picture.fanfic-hat-cover-picture img");
        if (cover != null) {
            ret.setCover(Urls.parseUrl(cover.attr("src")));
        }
        return ret;
    }
}
