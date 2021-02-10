package com.github.romanqed.api.html.entites;

import com.github.romanqed.api.AbstractLinkable;
import com.github.romanqed.api.interfaces.HtmlBuilder;
import com.github.romanqed.api.util.Checks;
import com.github.romanqed.api.util.ParseUtil;
import com.github.romanqed.api.util.Urls;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.URL;
import java.util.Date;

public class User extends AbstractLinkable {
    public static final HtmlBuilder<User> BUILDER = new UserBuilder();
    private String name;
    private URL avatar;
    private String about;
    private Date lastOnline;
    private String bankDetails;
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

    public String getBankDetails() {
        return bankDetails;
    }

    public Date getLastOnline() {
        return lastOnline;
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
            ret.about = Checks.requireNonExcept(
                    () -> page.selectFirst("article.profile-container div.urlize").wholeText(),
                    ""
            );
            ret.favourites = Checks.requireNonExcept(
                    () -> ParseUtil.checkedMixedNum(page.selectFirst("span.add-favourite-author-text a").text()),
                    0
            );
            ret.bankDetails = Checks.requireNonExcept(
                    () -> page.selectFirst("section.mb-30:contains(Поддержать) div.urlize").text(),
                    ""
            );
            ret.lastOnline = ParseUtil.parseNativeDate(Checks.requireNonExcept(
                    () -> page.selectFirst("section.mb-30 p span").attr("title"),
                    ""
            ));
            Element betaForm = page.selectFirst(ParseUtil.BETA_FORM_QUERY);
            if (betaForm != null) {
                ret.betaForm = BetaForm.BUILDER.build(betaForm);
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
