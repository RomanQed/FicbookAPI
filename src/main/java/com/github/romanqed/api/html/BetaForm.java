package com.github.romanqed.api.html;

import com.github.romanqed.api.AbstractLinkable;
import com.github.romanqed.api.html.entites.Tag;
import com.github.romanqed.api.html.entites.User;
import com.github.romanqed.api.interfaces.HtmlBuilder;
import com.github.romanqed.api.states.Direction;
import com.github.romanqed.api.util.Checks;
import com.github.romanqed.api.util.ParseUtil;
import com.github.romanqed.api.util.Urls;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class BetaForm extends AbstractLinkable {
    public static final HtmlBuilder<BetaForm> BUILDER = new BetaFormBuilder();
    private final Set<String> fandoms;
    private final Set<Tag> preferredTags;
    private final Set<Tag> avoidedTags;
    private final Set<Direction> directions;
    private String positiveQualities;
    private String negativeQualities;
    private String preferences;
    private String avoided;
    private int testResult;

    public BetaForm(URL url) {
        this.url = Checks.requireCorrectValue(url, User::validateUrl);
        fandoms = new HashSet<>();
        preferredTags = new HashSet<>();
        avoidedTags = new HashSet<>();
        directions = new HashSet<>();
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

    @Override
    public String toString() {
        return "[BetaForm] [Test result] " + testResult + " " + super.toString();
    }

    public static class BetaFormBuilder implements HtmlBuilder<BetaForm> {
        @Override
        public BetaForm build(URL url, Document page) {
            return build(page.selectFirst(ParseUtil.BETA_FORM_QUERY));
        }

        @Override
        public BetaForm build(Element node) {
            BetaForm ret = new BetaForm(Urls.parseFicbookUrl(node.selectFirst("div a.title").attr("href")));
            Elements info = node.select("div.beta_thumb_info");
            Elements fandoms = info.first().select("span");
            fandoms.forEach(fandom -> ret.fandoms.add(fandom.text()));
            Elements directions = info.select("span.help");
            directions.forEach(direction -> ret.directions.add(Direction.fromName(ParseUtil.safetyText(direction.text()))));
            Elements allTags = info.select("div.beta_thumb_info div.tags");
            Elements preTags = Checks.requireNonExcept(() -> allTags.get(0).children(), null);
            ParseUtil.parseHtmlNodes(Tag.BUILDER, preTags, ret.preferredTags);
            Elements aTags = Checks.requireNonExcept(() -> allTags.get(1).children(), null);
            ParseUtil.parseHtmlNodes(Tag.BUILDER, aTags, ret.avoidedTags);
            int length = info.size();
            ret.positiveQualities = info.get(length - 6).text();
            ret.negativeQualities = info.get(length - 5).text();
            ret.preferences = info.get(length - 4).text();
            ret.avoided = info.get(length - 3).text();
            ret.testResult = Checks.requireNonExcept(
                    () -> ParseUtil.parseMixedNum(info.get(length - 2).text()),
                    0
            );
            return ret;
        }
    }
}