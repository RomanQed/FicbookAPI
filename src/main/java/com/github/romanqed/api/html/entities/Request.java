package com.github.romanqed.api.html.entities;

import com.github.romanqed.api.AbstractIdentifiable;
import com.github.romanqed.api.enums.Direction;
import com.github.romanqed.api.enums.Rating;
import com.github.romanqed.api.html.builders.RequestBuilder;
import com.github.romanqed.api.interfaces.HtmlPageBuilder;
import com.github.romanqed.api.interfaces.Loadable;
import com.github.romanqed.api.util.Urls;

import java.net.URL;
import java.util.*;

public class Request extends AbstractIdentifiable implements Loadable {
    public static final HtmlPageBuilder<Request> BUILDER = new RequestBuilder();
    public final Set<Fandom> fandoms;
    public final List<String> characters;
    public final Set<Direction> directions;
    public final Set<Rating> ratings;
    public final Set<Tag> tags;
    public String title;
    public boolean isPremium;
    public User author;
    public int likes;
    public int works;
    public int bookmarks;
    public String description;
    public String hiddenDescription;
    public Date creationDate;

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
