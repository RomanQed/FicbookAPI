package com.github.romanqed.api.html;

import com.github.romanqed.api.util.Checks;
import com.github.romanqed.api.util.Urls;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.URL;

public class User extends AbstractHtmlBased {
    private String name;
    private URL avatar;
    private String about = "";
    private int favourites = 0;
    private BetaForm betaForm;

    public User(URL url) {
        this.url = Checks.requireCorrectValue(url, User::validateUrl);
    }

    public User(Element htmlElement) {
        url = Urls.parseAndValidateUrl(htmlElement.attr("href"), User::validateUrl);
        name = htmlElement.text();
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
    protected void fromPage(Document page) {
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
