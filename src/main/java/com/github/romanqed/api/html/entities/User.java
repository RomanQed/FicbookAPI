package com.github.romanqed.api.html.entities;

import com.github.romanqed.api.AbstractIdentifiable;
import com.github.romanqed.api.html.builders.UserBuilder;
import com.github.romanqed.api.interfaces.HtmlPageBuilder;
import com.github.romanqed.api.interfaces.Loadable;
import com.github.romanqed.api.util.Urls;

import java.net.URL;
import java.util.Date;

public class User extends AbstractIdentifiable implements Loadable {
    public static final HtmlPageBuilder<User> BUILDER = new UserBuilder();
    public String name;
    public URL avatar;
    public String about;
    public Date lastOnline;
    public String bankDetails;
    public int favourites;
    public BetaForm betaForm;

    public User(String id) {
        super(id);
        name = "";
        avatar = Urls.DEFAULT_AVATAR;
        about = "";
        favourites = 0;
    }

    @Override
    public String toString() {
        return "[User] " + name + " [Avatar] " + avatar + " [About] " + about + " " + super.toString();
    }

    @Override
    public URL getUrl() {
        return Urls.attachUrl(Urls.AUTHORS, id);
    }

    @Override
    public HtmlPageBuilder<?> getBuilder() {
        return BUILDER;
    }
}
