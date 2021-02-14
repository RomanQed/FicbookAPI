package com.github.romanqed.api.html.entities;

import com.github.romanqed.api.AbstractIdentifiable;
import com.github.romanqed.api.html.builders.UserBuilder;
import com.github.romanqed.api.interfaces.HtmlPageBuilder;
import com.github.romanqed.api.interfaces.Loadable;
import com.github.romanqed.api.util.Checks;
import com.github.romanqed.api.util.Urls;

import java.net.URL;
import java.util.Date;

public class User extends AbstractIdentifiable implements Loadable {
    public static final HtmlPageBuilder<User> BUILDER = new UserBuilder();
    private String name;
    private URL avatar;
    private String about;
    private Date lastOnline;
    private String bankDetails;
    private int favourites;
    private BetaForm betaForm;

    public User(String id) {
        super(id);
        name = "";
        avatar = Urls.DEFAULT_AVATAR;
        about = "";
        favourites = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = Checks.requireNonNullString(name);
    }

    public URL getAvatarUrl() {
        return avatar;
    }

    public void setAvatarUrl(URL avatarUrl) {
        this.avatar = avatarUrl;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = Checks.requireNonNullString(about);
    }

    public int getFavourites() {
        return favourites;
    }

    public void setFavourites(int favourites) {
        this.favourites = Math.max(0, favourites);
    }

    public BetaForm getBetaForm() {
        return betaForm;
    }

    public void setBetaForm(BetaForm betaForm) {
        this.betaForm = betaForm;
    }

    public boolean canBeBeta() {
        return betaForm != null;
    }

    public String getBankDetails() {
        return bankDetails;
    }

    public void setBankDetails(String bankDetails) {
        this.bankDetails = Checks.requireNonNullString(bankDetails);
    }

    public Date getLastOnline() {
        return lastOnline;
    }

    public void setLastOnline(Date lastOnline) {
        this.lastOnline = lastOnline;
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
