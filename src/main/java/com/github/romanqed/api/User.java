package com.github.romanqed.api;

import com.github.romanqed.api.states.Direction;
import com.github.romanqed.api.util.Checks;
import com.github.romanqed.api.util.Urls;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class User extends AbstractHtmlBased {
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
        betaForm = new BetaForm();
        Elements betaElements = rawBetaForm.select("div.beta_thumb_info");
        Elements fandoms = betaElements.first().select("span");
        fandoms.forEach(fandom -> betaForm.fandoms.add(fandom.text()));
        Elements tags = betaElements.select("a.tag");
        tags.forEach(tag -> betaForm.tags.add(new Tag(tag)));
        betaForm.positiveQualities = betaElements.get(3).text();
        betaForm.negativeQualities = betaElements.get(4).text();
        betaForm.preferences = betaElements.get(5).text();
        betaForm.avoided = betaElements.get(6).text();
        betaForm.testResult = Checks.requireNonExcept(
                () -> Integer.parseInt(betaElements.get(7).text().replaceAll("\\D", "")),
                0
        );
    }

    @Override
    public String toString() {
        return "[User] " + name + " [Avatar] " + avatar + " [About] " + about + " " + super.toString();
    }

    public static class BetaForm {
        private final Set<String> fandoms = new HashSet<>();
        private final Set<Tag> tags = new HashSet<>();
        private final Set<Direction> directions = new HashSet<>();
        private String positiveQualities;
        private String negativeQualities;
        private String preferences;
        private String avoided;
        private int testResult;

        public Set<String> getFandoms() {
            return fandoms;
        }

        public Set<Tag> getTags() {
            return tags;
        }

        public Set<Direction> getDirections() {
            return directions;
        }

        public String getPositiveQualities() {
            return positiveQualities;
        }

        public String getNegativeQualities() {
            return negativeQualities;
        }

        public String getPreferences() {
            return preferences;
        }

        public String getAvoided() {
            return avoided;
        }

        public int getTestResult() {
            return testResult;
        }
    }
}
