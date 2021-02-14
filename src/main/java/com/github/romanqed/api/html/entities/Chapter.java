package com.github.romanqed.api.html.entities;

import com.github.romanqed.api.AbstractIdentifiable;
import com.github.romanqed.api.html.builders.ChapterBuilder;
import com.github.romanqed.api.interfaces.HtmlPageBuilder;
import com.github.romanqed.api.interfaces.Loadable;
import com.github.romanqed.api.util.Checks;
import com.github.romanqed.api.util.Urls;

import java.net.URL;
import java.util.Date;

public class Chapter extends AbstractIdentifiable implements Loadable {
    public static final HtmlPageBuilder<Chapter> BUILDER = new ChapterBuilder();
    private String title;
    private Date date;
    private String body;
    private String notes;

    public Chapter(String id) {
        super(id);
        title = "";
        date = new Date();
        body = "";
        notes = "";
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getNotes() {
        return notes;
    }

    public Date getDate() {
        return date;
    }

    public void setTitle(String title) {
        this.title = Checks.requireNonNullString(title);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setBody(String body) {
        this.body = Checks.requireNonNullString(body);
    }

    public void setNotes(String notes) {
        this.notes = Checks.requireNonNullString(notes);
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
