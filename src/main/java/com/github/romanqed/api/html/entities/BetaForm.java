package com.github.romanqed.api.html.entities;

import com.github.romanqed.api.AbstractIdentifiable;
import com.github.romanqed.api.enums.Direction;
import com.github.romanqed.api.html.builders.BetaFormBuilder;
import com.github.romanqed.api.interfaces.HtmlPageBuilder;
import com.github.romanqed.api.interfaces.Loadable;
import com.github.romanqed.api.util.Checks;
import com.github.romanqed.api.util.Urls;

import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class BetaForm extends AbstractIdentifiable implements Loadable {
    public static final HtmlPageBuilder<BetaForm> BUILDER = new BetaFormBuilder();
    private String betaName;
    private final Set<String> fandoms;
    private final Set<Tag> preferredTags;
    private final Set<Tag> avoidedTags;
    private final Set<Direction> directions;
    private String positiveQualities;
    private String negativeQualities;
    private String preferences;
    private String avoided;
    private int testResult;

    public BetaForm(String id) {
        super(id);
        fandoms = new HashSet<>();
        preferredTags = new HashSet<>();
        avoidedTags = new HashSet<>();
        directions = new HashSet<>();
    }

    public String getBetaName() {
        return betaName;
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

    public void setBetaName(String betaName) {
        this.betaName = Checks.requireNonNullString(betaName);
    }

    public void setPositiveQualities(String positiveQualities) {
        this.positiveQualities = Checks.requireNonNullString(positiveQualities);
    }

    public void setNegativeQualities(String negativeQualities) {
        this.negativeQualities = Checks.requireNonNullString(negativeQualities);
    }

    public void setPreferences(String preferences) {
        this.preferences = Checks.requireNonNullString(preferences);
    }

    public void setAvoided(String avoided) {
        this.avoided = Checks.requireNonNullString(avoided);
    }

    public void setTestResult(int testResult) {
        this.testResult = Math.max(0, testResult);
    }

    @Override
    public String toString() {
        return "[BetaForm] " + betaName + " [Test result] " + testResult + " " + super.toString();
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