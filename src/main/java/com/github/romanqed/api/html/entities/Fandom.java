package com.github.romanqed.api.html.entities;

import com.github.romanqed.api.AbstractIdentifiable;
import com.github.romanqed.api.html.builders.FandomBuilder;
import com.github.romanqed.api.interfaces.HtmlPageBuilder;
import com.github.romanqed.api.interfaces.Loadable;
import com.github.romanqed.api.util.Urls;

import java.net.URL;

public class Fandom extends AbstractIdentifiable implements Loadable {
    public static final HtmlPageBuilder<Fandom> BUILDER = new FandomBuilder();
    public String title;
    public int fictionId;

    public Fandom(String id) {
        super(id);
        title = "";
    }

    @Override
    public String toString() {
        return "[Fandom] " + title + " " + super.toString();
    }

    @Override
    public URL getUrl() {
        return Urls.attachUrl(Urls.FANDOMS, id);
    }

    @Override
    public HtmlPageBuilder<?> getBuilder() {
        return BUILDER;
    }
}
