package com.github.romanqed.api.html.entites;

import com.github.romanqed.api.html.AbstractHtmlBased;
import com.github.romanqed.api.states.Direction;
import com.github.romanqed.api.states.Rating;
import com.github.romanqed.api.util.Urls;

import java.net.URL;
import java.util.*;

public class Request extends AbstractHtmlBased {
    private final Set<Fandom> fandoms = new HashSet<>();
    private final List<Pairing> characters = new ArrayList<>();
    private final Set<Direction> directions = new HashSet<>();
    private final Set<Rating> ratings = new HashSet<>();
    private final Set<Tag> tags = new HashSet<>();
    private String title;
    private User author;
    private int likes;
    private int works;
    private int bookmarks;
    private String description;
    private String hiddenDescription;
    private Date creationDate;

    public Request(URL url) {
        // TODO
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

    public List<Pairing> getCharacters() {
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
}
