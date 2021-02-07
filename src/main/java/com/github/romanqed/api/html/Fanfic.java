package com.github.romanqed.api.html;

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
    private boolean isTranslate;
    private String originalAuthor = "";
    private String originalFanfic = "";
    private boolean isPremium;
    private Size size;
    private int actualPages;
    private int likes;
    private int inCollections;
    private String description;
    private String dedication = "";
    private String notes = "";
    private String copyright = "";

    public Fanfic(Element htmlElement) {
        isTranslate = htmlElement.selectFirst("span.notice-blue") != null;
        isPremium = htmlElement.selectFirst("a.notice-yellow") != null;
        Element head = htmlElement.selectFirst("h3");
        Element link = head.selectFirst("a");
        url = Urls.parseAndValidateUrl(link.attr("href"), Fanfic::validateUrl);
        title = link.text();
        direction = Direction.fromName(head.selectFirst("svg").attr("class").split(" ")[0]);
        likes = Checks.requireNonExcept(() -> Integer.parseInt(head.selectFirst("sup.count span.value").text()), 0);
        Elements authors = htmlElement.select("span.author a");
        if (isTranslate) {
            this.authors.put(new User(authors.first()), AuthorRole.TRANSLATOR);
            originalAuthor = authors.last().text();
        } else {
            this.authors.put(new User(authors.first()), AuthorRole.AUTHOR);
        }
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
    protected void fromPage(Document page) {
        Element mainInfo = page.selectFirst("div.fanfic-main-info");
        title = mainInfo.selectFirst("h1").text();
        Elements fandoms = mainInfo.select("div.mb-10 a");
        for (Element fandom : fandoms) {
            this.fandoms.add(new Fandom(fandom));
        }
        direction = Direction.fromName(ParseUtil.safetyText(mainInfo.selectFirst("div.direction svg").attr("class")));
        rating = Rating.fromName(ParseUtil.safetyText(mainInfo.selectFirst("strong span").text()));
        isTranslate = mainInfo.selectFirst("span.badge-translate") != null;
        status = Status.fromName(ParseUtil.safetyText(mainInfo.selectFirst("span.badge-secondary svg").attr("class")));
        likes = Checks.requireNonExcept(() -> Integer.parseInt(mainInfo.selectFirst("span.badge-like").text()), 0);
        inCollections = Checks.requireNonExcept(() -> ParseUtil.parseMixedNum(page.selectFirst("span.main-info svg.ic_bookmark").parent().text()), 0);
        Element hat = page.selectFirst("section.fanfic-hat");
        isPremium = hat.selectFirst("div.fanfic-hat-premium-notice") != null;
        if (isTranslate) {
            Element info = hat.selectFirst("div.mb-15");
            originalAuthor = info.selectFirst("a").text();
            originalFanfic = info.selectFirst("div.urlize").text();
        }
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
        dedication = Checks.requireNonExcept(() -> hat.selectFirst("div.mb-5:contains(Посвящение)").wholeText(), "");
        notes = Checks.requireNonExcept(() -> hat.selectFirst("div.mb-5:contains(Примечания)").wholeText(), "");
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

    @Override
    public String toString() {
        return "[Fanfic] " + title + " [Description] " + description + " " + super.toString();
    }
}
