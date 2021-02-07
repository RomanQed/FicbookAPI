package com.github.romanqed.api.html;

import com.github.romanqed.api.states.Direction;
import com.github.romanqed.api.util.Checks;
import com.github.romanqed.api.util.ParseUtil;
import org.jsoup.select.Elements;

import java.util.HashSet;
import java.util.Set;

public class BetaForm {
    private final Set<String> fandoms = new HashSet<>();
    private final Set<Tag> preferredTags = new HashSet<>();
    private final Set<Tag> avoidedTags = new HashSet<>();
    private final Set<Direction> directions = new HashSet<>();
    private final String positiveQualities;
    private final String negativeQualities;
    private final String preferences;
    private final String avoided;
    private final int testResult;

    public BetaForm(Elements betaElements) {
        Elements fandoms = betaElements.first().select("span");
        fandoms.forEach(fandom -> this.fandoms.add(fandom.text()));
        Elements directions = betaElements.select("span.help");
        directions.forEach(direction -> this.directions.add(Direction.fromName(ParseUtil.safetyText(direction.text()))));
        Elements allTags = betaElements.select("div.beta_thumb_info div.tags");
        Elements preTags = Checks.requireNonExcept(() -> allTags.get(0).children(), null);
        ParseUtil.parseHtmlNodes(Tag.BUILDER, preTags, preferredTags);
        Elements aTags = Checks.requireNonExcept(() -> allTags.get(1).children(), null);
        ParseUtil.parseHtmlNodes(Tag.BUILDER, aTags, avoidedTags);
        int length = betaElements.size();
        positiveQualities = betaElements.get(length - 6).text();
        negativeQualities = betaElements.get(length - 5).text();
        preferences = betaElements.get(length - 4).text();
        avoided = betaElements.get(length - 3).text();
        testResult = Checks.requireNonExcept(
                () -> ParseUtil.parseMixedNum(betaElements.get(length - 2).text()),
                0
        );
    }

    public Set<String> getFandoms() {
        return fandoms;
    }

    public Set<Tag> getPreferredTags() {
        return preferredTags;
    }

    public Set<Tag> getAvoidedTags() {
        return avoidedTags;
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