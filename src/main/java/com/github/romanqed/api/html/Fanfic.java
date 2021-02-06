package com.github.romanqed.api.html;

import com.github.romanqed.api.json.Reward;
import com.github.romanqed.api.states.*;
import com.github.romanqed.api.util.Checks;
import com.github.romanqed.api.util.ParseUtil;
import com.github.romanqed.api.util.Urls;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Fanfic extends AbstractHtmlBased {
    private final Set<Fandom> fandoms = new HashSet<>();
    private final Map<User, AuthorRole> authors = new ConcurrentHashMap<>();
    private final List<Pairing> pairings = new ArrayList<>();
    private final Set<Tag> tags = new HashSet<>();
    private final List<Reward> rewards = new ArrayList<>();
    private final List<Chapter> chapters = new ArrayList<>();
    private String title;
    private Direction direction;
    private Rating rating;
    private Status status;
    private Size size;
    private int actualPages;
    private int likes;
    private int inCollections;
    private String description;
    private String dedication = "";
    private String notes = "";
    private String copyright = "";

    public Fanfic(Element htmlElement) {
        Element head = htmlElement.selectFirst("h3");
        Element link = head.selectFirst("a");
        url = Urls.parseAndValidateUrl(link.attr("href"), Fanfic::validateUrl);
        title = link.text();
        direction = Direction.fromName(head.selectFirst("svg").attr("class").split(" ")[0]);
        likes = Checks.requireNonExcept(() -> Integer.parseInt(head.selectFirst("sup.count span.value").text()), 0);
        authors.put(new User(htmlElement.selectFirst("div.authors-list a")), AuthorRole.AUTHOR);
        Elements hat = htmlElement.select("dl.info dd");
        Elements fandoms = hat.get(0).select("a");
        for (Element fandom : fandoms) {
            this.fandoms.add(new Fandom(fandom));
        }
        Elements pairings = hat.select("a.pairing-link");
        for (Element pairing : pairings) {
            this.pairings.add(new Pairing(pairing));
        }
        Elements rs = hat.select("strong[title]");
        rating = Rating.fromName(ParseUtil.safetyText(rs.first().text()));
        Element size = rs.last();
        this.size = Size.fromName(ParseUtil.safetyText(size.text()));
        actualPages = Checks.requireNonExcept(
                () -> ParseUtil.parseMixedNum(size.parent().text().split(",")[1]),
                0
        );
        status = Status.fromName(ParseUtil.safetyText(hat.select("span[style]").first().text()));
        Elements tags = hat.select("a.tag");
        for (Element tag : tags) {
            this.tags.add(new Tag(tag));
        }
        description = htmlElement.selectFirst("div.fanfic-description").text();
    }

    public Fanfic(URL url) {
        this.url = Checks.requireCorrectValue(url, Fanfic::validateUrl);
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
    protected void fromPage(String rawPage) {
        Document page = Jsoup.parse(rawPage);
        Element mainInfo = page.selectFirst("div.fanfic-main-info");
        title = mainInfo.selectFirst("h1").text();
        Elements fandoms = mainInfo.select("div.mb-10 a");
        for (Element fandom : fandoms) {
            this.fandoms.add(new Fandom(fandom));
        }
        Elements badges = mainInfo.select("div.badge-icon-container svg");
        direction = Direction.fromName(ParseUtil.safetyText(badges.get(0).attr("class")));
        rating = Rating.fromName(ParseUtil.safetyText(mainInfo.selectFirst("strong span").text()));
        status = Status.fromName(ParseUtil.safetyText(badges.get(1).attr("class")));
        likes = Checks.requireNonExcept(() -> Integer.parseInt(mainInfo.selectFirst("span.badge-like").text()), 0);
        inCollections = Checks.requireNonExcept(() -> ParseUtil.parseMixedNum(page.selectFirst("span.main-info svg.ic_bookmark").parent().text()), 0);
        Element hat = page.selectFirst("section.fanfic-hat");
        Element rewardElements = hat.selectFirst("fanfic-reward-list");
        if (rewardElements != null) {
            JsonElement rewards = JsonParser.parseString(rewardElements.attr(":initial-fic-rewards-list"));
            if (rewards != null && rewards.isJsonArray()) {
                rewards.getAsJsonArray().forEach(reward -> this.rewards.add(new Reward(reward.getAsJsonObject())));
            }
        }
        Elements authors = hat.select("div.hat-creator-container div.creator-info");
        for (Element author : authors) {
            this.authors.put(new User(author.selectFirst("a")), AuthorRole.fromName(author.selectFirst("i").text()));
        }
        Elements pairings = hat.select("a.pairing-link");
        for (Element pairing : pairings) {
            this.pairings.add(new Pairing(pairing));
        }
        Element size = hat.selectFirst("div.mb-5 div span");
        this.size = Size.fromName(ParseUtil.safetyText(size.text()));
        actualPages = ParseUtil.parseMixedNum(size.parent().text().split(",")[1]);
        Elements tags = hat.select("div.tags a");
        for (Element tag : tags) {
            this.tags.add(new Tag(tag));
        }
        description = hat.selectFirst("div[itemprop=description]").text();
        Elements checked = hat.select("div.urlize[itemprop!=description]");
        if (checked.size() == 2) {
            dedication = checked.first().text();
            notes = checked.last().text();
        } else if (checked.size() == 1) {
            String checkedText = ParseUtil.safetyText(checked.first().parent().text());
            if (checkedText.startsWith("посвящение")) {
                dedication = checked.first().text();
            } else {
                notes = checked.first().text();
            }
        }
        copyright = hat.select("div.mb-5 div").last().text();
        Elements chapters = page.select("article li.part");
        if (!chapters.isEmpty()) {
            for (Element chapter : chapters) {
                this.chapters.add(new Chapter(chapter));
            }
        } else {
            this.chapters.add(new Chapter(url));
        }
    }
}
