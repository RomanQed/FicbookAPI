package com.github.romanqed.api.html.entities;

import com.github.romanqed.api.AbstractIdentifiable;
import com.github.romanqed.api.enums.*;
import com.github.romanqed.api.html.builders.FanficBuilder;
import com.github.romanqed.api.interfaces.HtmlPageBuilder;
import com.github.romanqed.api.interfaces.Loadable;
import com.github.romanqed.api.json.Reward;
import com.github.romanqed.api.util.Urls;

import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Fanfic extends AbstractIdentifiable implements Loadable {
    public static final HtmlPageBuilder<Fanfic> BUILDER = new FanficBuilder();
    public final Set<Fandom> fandoms;
    public final Map<User, AuthorRole> authors;
    public final List<Pairing> pairings;
    public final Set<Tag> tags;
    public final List<Reward> rewards;
    public final List<Chapter> chapters;
    public String title;
    public Direction direction;
    public Rating rating;
    public Status status;
    public boolean isTranslate;
    public String originalAuthor;
    public String originalFanfic;
    public boolean isPremium;
    public Size size;
    public int actualPages;
    public int likes;
    public int inCollections;
    public String description;
    public String dedication;
    public String notes;
    public String copyright;
    public URL cover;

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
