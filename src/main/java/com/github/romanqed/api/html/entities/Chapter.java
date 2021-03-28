package com.github.romanqed.api.html.entities;

import com.github.romanqed.api.AbstractIdentifiable;
import com.github.romanqed.api.html.builders.ChapterBuilder;
import com.github.romanqed.api.interfaces.HtmlPageBuilder;
import com.github.romanqed.api.interfaces.Loadable;
import com.github.romanqed.api.util.Urls;

import java.net.URL;
import java.util.Date;

public class Chapter extends AbstractIdentifiable implements Loadable {
    public static final HtmlPageBuilder<Chapter> BUILDER = new ChapterBuilder();
    public String title;
    public Date date;
    public String body;
    public String notes;

    public Chapter(String id) {
        super(id);
        title = "";
        date = new Date();
        body = "";
        notes = "";
    }

    @Override
    public String toString() {
        return "[Chapter] " + title + " " + super.toString();
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
