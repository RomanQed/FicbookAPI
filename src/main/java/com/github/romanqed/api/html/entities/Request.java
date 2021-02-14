package com.github.romanqed.api.html.entities;

import com.github.romanqed.api.AbstractIdentifiable;
import com.github.romanqed.api.enums.Direction;
import com.github.romanqed.api.enums.Rating;
import com.github.romanqed.api.html.builders.RequestBuilder;
import com.github.romanqed.api.interfaces.HtmlPageBuilder;
import com.github.romanqed.api.interfaces.Loadable;
import com.github.romanqed.api.util.Checks;
import com.github.romanqed.api.util.Urls;

import java.net.URL;
import java.util.*;

public class Request extends AbstractIdentifiable implements Loadable {
    public static final HtmlPageBuilder<Request> BUILDER = new RequestBuilder();
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

    public Request(String id) {
        super(id);
        fandoms = new HashSet<>();
        characters = new ArrayList<>();
        directions = new HashSet<>();
        ratings = new HashSet<>();
        tags = new HashSet<>();
        title = "";
        creationDate = new Date();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = Checks.requireNonNullString(title);
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = Math.max(0, likes);
    }

    public int getWorks() {
        return works;
    }

    public void setWorks(int works) {
        this.works = Math.max(0, works);
    }

    public int getBookmarks() {
        return bookmarks;
    }

    public void setBookmarks(int bookmarks) {
        this.bookmarks = Math.max(0, bookmarks);
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

    public void setDescription(String description) {
        this.description = Checks.requireNonNullString(description);
    }

    public String getHiddenDescription() {
        return hiddenDescription;
    }

    public void setHiddenDescription(String hiddenDescription) {
        this.hiddenDescription = Checks.requireNonNullString(hiddenDescription);
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isPremium() {
        return isPremium;
    }

    public void setPremium(boolean premium) {
        isPremium = premium;
    }

    @Override
    public String toString() {
        return "[Request] " + title + " [Description] " + description + " " + super.toString();
    }

    @Override
    public URL getUrl() {
        return Urls.attachUrl(Urls.REQUESTS, id);
    }

    @Override
    public HtmlPageBuilder<?> getBuilder() {
        return BUILDER;
    }
}
