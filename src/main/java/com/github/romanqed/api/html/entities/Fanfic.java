package com.github.romanqed.api.html.entities;

import com.github.romanqed.api.AbstractIdentifiable;
import com.github.romanqed.api.enums.*;
import com.github.romanqed.api.html.builders.FanficBuilder;
import com.github.romanqed.api.interfaces.HtmlPageBuilder;
import com.github.romanqed.api.interfaces.Loadable;
import com.github.romanqed.api.json.Reward;
import com.github.romanqed.api.util.Checks;
import com.github.romanqed.api.util.Urls;

import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Fanfic extends AbstractIdentifiable implements Loadable {
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

    public Fanfic(String id) {
        super(id);
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

    public void setTitle(String title) {
        this.title = Checks.requireNonNullString(title);
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setTranslate(boolean translate) {
        isTranslate = translate;
    }

    public void setOriginalAuthor(String originalAuthor) {
        this.originalAuthor = Checks.requireNonNullString(originalAuthor);
    }

    public void setOriginalFanfic(String originalFanfic) {
        this.originalFanfic = Checks.requireNonNullString(originalFanfic);
    }

    public void setPremium(boolean premium) {
        isPremium = premium;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public void setActualPages(int actualPages) {
        this.actualPages = Math.max(0, actualPages);
    }

    public void setLikes(int likes) {
        this.likes = Math.max(0, likes);
    }

    public void setInCollections(int inCollections) {
        this.inCollections = Math.max(0, inCollections);
    }

    public void setDescription(String description) {
        this.description = Checks.requireNonNullString(description);
    }

    public void setDedication(String dedication) {
        this.dedication = Checks.requireNonNullString(dedication);
    }

    public void setNotes(String notes) {
        this.notes = Checks.requireNonNullString(notes);
    }

    public void setCopyright(String copyright) {
        this.copyright = Checks.requireNonNullString(copyright);
    }

    @Override
    public String toString() {
        return "[Fanfic] " + title + " [Description] " + description + " " + super.toString();
    }

    @Override
    public URL getUrl() {
        return Urls.attachUrl(Urls.FANFIC, id);
    }

    @Override
    public HtmlPageBuilder<?> getBuilder() {
        return BUILDER;
    }
}
