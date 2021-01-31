package com.github.romanqed.api;

import com.github.romanqed.api.util.Checks;
import com.github.romanqed.api.util.Urls;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.URL;

public class User extends LinkableHtmlBased {
    private String name;
    private URL avatar;
    private String about = "";
    private int favourites = 0;
    private BetaForm betaForm;

    public User(URL link) {
        this.link = Checks.requireCorrectValue(link, User::validateUrl);
    }

    public User(int id) {
        link = Urls.attachUrl(Urls.AUTHORS, Integer.toString(Checks.requireCorrectValue(id, rawId -> rawId > 0)));
    }

    public User(Element htmlElement) {
        // TODO
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
    public boolean fullLoaded() {
        return !name.isEmpty() && avatar != null;
    }

    @Override
    protected void fromPage(String rawPage) {
        Document page = Jsoup.parse(rawPage);
        name = page.selectFirst("div.author-hat h1").text();
        avatar = Urls.parseUrl(page.selectFirst("img[alt=" + name + "]").attr("src"));
        about = Checks.requireNonExcept(() -> page.selectFirst("article.profile-container div.urlize").wholeText(), "");
        favourites = Checks.requireNonExcept(
                () -> Integer.parseInt(page.selectFirst("span.add-favourite-author-text a").text().replaceAll("\\D", "")),
                0
        );
        Element rawBetaForm = page.selectFirst("div.beta_thumb");
        if (rawBetaForm == null) {
            return;
        }
        betaForm = new BetaForm(rawBetaForm.select("div.beta_thumb_info"));
    }

    @Override
    public String toString() {
        return "[User] " + name + " [Avatar] " + avatar + " [About] " + about + " " + super.toString();
    }
}
