package com.github.romanqed.api.html.entites;

import com.github.romanqed.api.AbstractLinkable;
import com.github.romanqed.api.interfaces.HtmlBuilder;
import com.github.romanqed.api.states.Direction;
import com.github.romanqed.api.states.Rating;
import com.github.romanqed.api.util.Checks;
import com.github.romanqed.api.util.ParseUtil;
import com.github.romanqed.api.util.Urls;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.*;

public class Request extends AbstractLinkable {
    public static final HtmlBuilder<Request> BUILDER = new RequestBuilder();
    private final Set<Fandom> fandoms;
    private final List<String> characters;
    private final Set<Direction> directions;
    private final Set<Rating> ratings;
    private final Set<Tag> tags;
    private String title;
    private boolean isPremium;
    private User author;
    private int likes;
    private int works;
    private int bookmarks;
    private String description;
    private String hiddenDescription;
    private Date creationDate;

    public Request(URL url) {
        this.url = Checks.requireCorrectValue(url, Request::validateUrl);
        fandoms = new HashSet<>();
        characters = new ArrayList<>();
        directions = new HashSet<>();
        ratings = new HashSet<>();
        tags = new HashSet<>();
        title = "";
        creationDate = new Date();
    }

    public static boolean validateUrl(URL url) {
        return Urls.validateChildUrl(Urls.REQUESTS, url);
    }

    public String getTitle() {
        return title;
    }

    public User getAuthor() {
        return author;
    }

    public int getLikes() {
        return likes;
    }

    public int getWorks() {
        return works;
    }

    public int getBookmarks() {
        return bookmarks;
    }

    public Set<Fandom> getFandoms() {
        return fandoms;
    }

    public List<String> getCharacters() {
        return characters;
    }

    public Set<Direction> getDirections() {
        return directions;
    }

    public Set<Rating> getRatings() {
        return ratings;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public String getDescription() {
        return description;
    }

    public String getHiddenDescription() {
        return hiddenDescription;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public boolean isPremium() {
        return isPremium;
    }

    @Override
    public String toString() {
        return "[Request] " + title + " [Description] " + description + " " + super.toString();
    }

    public static class RequestBuilder implements HtmlBuilder<Request> {
        @Override
        public Request build(URL url, Document page) {
            Request ret = new Request(url);
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
            spans.get(0).select("span.help").forEach(e -> ret.directions.add(Direction.fromName(ParseUtil.safetyText(e.text()))));
            spans.get(1).select("span.help").forEach(e -> ret.ratings.add(Rating.fromName(ParseUtil.safetyText(e.text()))));
            ParseUtil.parseHtmlNodes(Tag.BUILDER, content.select("a.tag"), ret.tags);
            Element characters = content.selectFirst(prefix + ":contains(Персонажи)");
            if (characters != null) {
                Collections.addAll(ret.characters, characters.text().split(","));
            }
            ret.description = content.selectFirst("div.word-break").wholeText();
            ret.hiddenDescription = Checks.requireNonExcept(() -> content.selectFirst("div.word-break[style]").wholeText(), "");
            ret.creationDate = ParseUtil.parseNativeDate(content.selectFirst("p span").attr("title"));
            return ret;
        }

        @Override
        public Request build(Element node) {
            // TODO
            return null;
        }
    }
}
