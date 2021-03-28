package com.github.romanqed.api.html.entities;

import com.github.romanqed.api.AbstractIdentifiable;
import com.github.romanqed.api.enums.Direction;
import com.github.romanqed.api.html.builders.BetaFormBuilder;
import com.github.romanqed.api.interfaces.HtmlPageBuilder;
import com.github.romanqed.api.interfaces.Loadable;
import com.github.romanqed.api.util.Urls;

import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class BetaForm extends AbstractIdentifiable implements Loadable {
    public static final HtmlPageBuilder<BetaForm> BUILDER = new BetaFormBuilder();
    public final Set<String> fandoms;
    public final Set<Tag> preferredTags;
    public final Set<Tag> avoidedTags;
    public final Set<Direction> directions;
    public String betaName;
    public String positiveQualities;
    public String negativeQualities;
    public String preferences;
    public String avoided;
    public int testResult;

    public BetaForm(String id) {
        super(id);
        fandoms = new HashSet<>();
        preferredTags = new HashSet<>();
        avoidedTags = new HashSet<>();
        directions = new HashSet<>();
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