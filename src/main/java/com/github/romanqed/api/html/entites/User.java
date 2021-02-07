package com.github.romanqed.api.html.entites;

import com.github.romanqed.api.AbstractLinkable;
import com.github.romanqed.api.html.BetaForm;
import com.github.romanqed.api.interfaces.HtmlBuilder;
import com.github.romanqed.api.util.Checks;
import com.github.romanqed.api.util.Urls;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.URL;

public class User extends AbstractLinkable {
    public static final HtmlBuilder<User> BUILDER = new UserBuilder();
    private String name;
    private URL avatar;
    private String about;
    private int favourites;
    private BetaForm betaForm;

    public User(URL url) {
        this.url = Checks.requireCorrectValue(url, User::validateUrl);
        name = "";
        avatar = Urls.DEFAULT_AVATAR;
        about = "";
        favourites = 0;
    }

    public static boolean validateUrl(URL url) {
        return Urls.validateChildUrl(Urls.AUTHORS, url);
    }

    public String getName() {
        return name;
    }

    public URL getAvatarUrl() {
        return avatar;
    }

    public String getAbout() {
        return about;
    }

    public int getFavourites() {
        return favourites;
    }

    public BetaForm getBetaForm() {
        return betaForm;
    }

    public boolean canBeBeta() {
        return betaForm != null;
    }

    @Override
    public String toString() {
        return "[User] " + name + " [Avatar] " + avatar + " [About] " + about + " " + super.toString();
    }

    public static class UserBuilder implements HtmlBuilder<User> {
        @Override
        public User build(URL url, Document page) {
            User ret = new User(url);
            ret.name = page.selectFirst("div.author-hat h1").text();
            ret.avatar = Urls.parseUrl(page.selectFirst("img[alt=" + ret.name + "]").attr("src"));
            ret.about = Checks.requireNonExcept(() -> page.selectFirst("article.profile-container div.urlize").wholeText(), "");
            ret.favourites = Checks.requireNonExcept(
                    () -> Integer.parseInt(page.selectFirst("span.add-favourite-author-text a").text().replaceAll("\\D", "")),
                    0
            );
            Element rawBetaForm = page.selectFirst("div.beta_thumb");
            if (rawBetaForm != null) {
                ret.betaForm = new BetaForm(rawBetaForm.select("div.beta_thumb_info"));
            }
            return ret;
        }

        @Override
        public User build(Element node) {
            User ret = new User(Urls.parseFicbookUrl(node.attr("href")));
            ret.name = node.text();
            return ret;
        }
    }
}
