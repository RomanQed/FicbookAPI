package com.github.romanqed.api.html.entites;

import com.github.romanqed.api.AbstractLinkable;
import com.github.romanqed.api.interfaces.HtmlPageBuilder;
import com.github.romanqed.api.json.Reward;
import com.github.romanqed.api.states.*;
import com.github.romanqed.api.util.Checks;
import com.github.romanqed.api.util.ParseUtil;
import com.github.romanqed.api.util.Urls;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Fanfic extends AbstractLinkable {
    public static final HtmlPageBuilder<Fanfic> BUILDER = new FanficBuilder();
    private final Set<Fandom> fandoms;
    private final Map<User, AuthorRole> authors;
    private final List<Pairing> pairings;
    private final Set<Tag> tags;
    private final List<Reward> rewards;
    private final List<Chapter> chapters;
    private String title;
    private Direction direction;
    private Rating rating;
    private Status status;
    private boolean isTranslate;
    private String originalAuthor;
    private String originalFanfic;
    private boolean isPremium;
    private Size size;
    private int actualPages;
    private int likes;
    private int inCollections;
    private String description;
    private String dedication;
    private String notes;
    private String copyright;

    public Fanfic(URL url) {
        this.url = Checks.requireCorrectValue(url, Fanfic::validateUrl);
        fandoms = new HashSet<>();
        authors = new ConcurrentHashMap<>();
        pairings = new ArrayList<>();
        tags = new HashSet<>();
        rewards = new ArrayList<>();
        chapters = new ArrayList<>();
        originalAuthor = "";
        originalFanfic = "";
        dedication = "";
        notes = "";
        copyright = "";
    }

    public static boolean validateUrl(URL url) {
        return Urls.validateChildUrl(Urls.FANFIC, url);
    }

    public String getTitle() {
        return title;
    }

    public Set<Fandom> getFandoms() {
        return fandoms;
    }

    public Direction getDirection() {
        return direction;
    }

    public Rating getRating() {
        return rating;
    }

    public boolean isTranslate() {
        return isTranslate;
    }

    public String getOriginalAuthor() {
        if (!isTranslate) {
            throw new IllegalStateException("Fanfic is not a translation");
        }
        return originalAuthor;
    }

    public String getOriginalFanfic() {
        if (!isTranslate) {
            throw new IllegalStateException("Fanfic is not a translation");
        }
        return originalFanfic;
    }

    public boolean isPremium() {
        return isPremium;
    }

    public Status getStatus() {
        return status;
    }

    public int getLikes() {
        return likes;
    }

    public int inCollections() {
        return inCollections;
    }

    public Map<User, AuthorRole> getAuthors() {
        return authors;
    }

    public List<Pairing> getPairings() {
        return pairings;
    }

    public Size getSize() {
        return size;
    }

    public int getActualPages() {
        return actualPages;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public String getDescription() {
        return description;
    }

    public String getDedication() {
        return dedication;
    }

    public String getNotes() {
        return notes;
    }

    public String getCopyright() {
        return copyright;
    }

    public List<Reward> getRewards() {
        return rewards;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    @Override
    public String toString() {
        return "[Fanfic] " + title + " [Description] " + description + " " + super.toString();
    }

    public static class FanficBuilder implements HtmlPageBuilder<Fanfic> {
        @Override
        public Fanfic build(URL url, Document page) {
            Fanfic ret = new Fanfic(url);
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
            ret.likes = Checks.requireNonExcept(
                    () -> Integer.parseInt(mainInfo.selectFirst("span.badge-like").text()),
                    0
            );
            ret.inCollections = Checks.requireNonExcept(
                    () -> ParseUtil.parseMixedNum(page.selectFirst("span.main-info:has(svg.ic_bookmark)").text()),
                    0
            );
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
                    rewards.getAsJsonArray().forEach(reward -> {
                        ret.rewards.add(new Reward(reward.getAsJsonObject()));
                    });
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
                ret.chapters.add(new Chapter(url));
            }
            return ret;
        }

        @Override
        public Fanfic build(Element node) {
            Element head = node.selectFirst("h3");
            Element link = head.selectFirst("a");
            Fanfic ret = new Fanfic(Urls.parseFicbookUrl(link.attr("href")));
            ret.isTranslate = node.selectFirst("span.notice-blue") != null;
            ret.isPremium = node.selectFirst("a.notice-yellow") != null;
            ret.title = link.text();
            ret.direction = Direction.fromName(head.selectFirst("svg").attr("class").split(" ")[0]);
            ret.likes = Checks.requireNonExcept(
                    () -> Integer.parseInt(head.selectFirst("sup.count span.value").text()),
                    0
            );
            Elements authors = node.select("span.author a");
            if (ret.isTranslate) {
                ret.authors.put(User.BUILDER.build(authors.first()), AuthorRole.TRANSLATOR);
                ret.originalAuthor = authors.last().text();
            } else {
                ret.authors.put(User.BUILDER.build(authors.first()), AuthorRole.AUTHOR);
            }
            Elements hat = node.select("dl.info dd");
            ParseUtil.parseHtmlNodes(Fandom.BUILDER, hat.get(0).select("a"), ret.fandoms);
            ParseUtil.parseHtmlNodes(Pairing.BUILDER, hat.select("a.pairing-link"), ret.pairings);
            Elements rs = hat.select("strong[title]");
            ret.rating = Rating.fromName(ParseUtil.safetyText(rs.first().text()));
            Element size = rs.last();
            ret.size = Size.fromName(ParseUtil.safetyText(size.text()));
            ret.actualPages = Checks.requireNonExcept(
                    () -> ParseUtil.parseMixedNum(size.parent().text().split(",")[1]),
                    0
            );
            ret.status = Status.fromName(ParseUtil.safetyText(hat.select("span[style]").first().text()));
            ParseUtil.parseHtmlNodes(Tag.BUILDER, hat.select("a.tag"), ret.tags);
            ret.description = node.selectFirst("div.fanfic-description").text();
            return ret;
        }
    }
}
