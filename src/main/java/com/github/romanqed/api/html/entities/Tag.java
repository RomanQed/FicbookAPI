package com.github.romanqed.api.html.entities;

import com.github.romanqed.api.AbstractIdentifiable;
import com.github.romanqed.api.html.builders.TagBuilder;
import com.github.romanqed.api.interfaces.HtmlPageBuilder;
import com.github.romanqed.api.interfaces.Loadable;
import com.github.romanqed.api.util.Checks;
import com.github.romanqed.api.util.Urls;

import java.net.URL;

public class Tag extends AbstractIdentifiable implements Loadable {
    public static final HtmlPageBuilder<Tag> BUILDER = new TagBuilder();
    private String title;
    private String description;

    public Tag(String id) {
        super(id);
        title = "";
        description = "";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = Checks.requireNonNullString(title);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = Checks.requireNonNullString(description);
    }

    @Override
    public String toString() {
        return "[Tag] " + title + " " + super.toString();
    }

    @Override
    public URL getUrl() {
        return Urls.attachUrl(Urls.TAGS, id);
    }

    @Override
    public HtmlPageBuilder<?> getBuilder() {
        return BUILDER;
    }
}
