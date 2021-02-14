package com.github.romanqed.api.html.builders;

import com.github.romanqed.api.enums.Direction;
import com.github.romanqed.api.html.entities.BetaForm;
import com.github.romanqed.api.html.entities.Tag;
import com.github.romanqed.api.interfaces.HtmlPageBuilder;
import com.github.romanqed.api.util.Checks;
import com.github.romanqed.api.util.ParseUtil;
import com.github.romanqed.api.util.Queries;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.Set;

public class BetaFormBuilder implements HtmlPageBuilder<BetaForm> {
    @Override
    public BetaForm build(URL url, Document page) {
        return build(page.selectFirst(Queries.BETA_FORM_QUERY));
    }

    @Override
    public BetaForm build(Element node) {
        Element title = node.selectFirst("div a.title");
        BetaForm ret = new BetaForm(ParseUtil.sliceLastPath(title.attr("href")));
        ret.setBetaName(title.text());
        Elements info = node.select("div.beta_thumb_info");
        Elements fandoms = info.first().select("span");
        Set<String> retFandoms = ret.getFandoms();
        fandoms.forEach(fandom -> retFandoms.add(fandom.text()));
        Elements directions = info.select("span.help");
        Set<Direction> retDirections = ret.getDirections();
        directions.forEach(direction -> retDirections.add(Direction.fromName(ParseUtil.safetyText(direction.text()))));
        Elements allTags = info.select("div.beta_thumb_info div.tags");
        Elements preTags = Checks.requireNonExcept(() -> allTags.get(0).children(), null);
        ParseUtil.parseHtmlNodes(Tag.BUILDER, preTags, ret.getPreferredTags());
        Elements aTags = Checks.requireNonExcept(() -> allTags.get(1).children(), null);
        ParseUtil.parseHtmlNodes(Tag.BUILDER, aTags, ret.getAvoidedTags());
        int length = info.size();
        ret.setPositiveQualities(info.get(length - 6).text());
        ret.setNegativeQualities(info.get(length - 5).text());
        ret.setPreferences(info.get(length - 4).text());
        ret.setAvoided(info.get(length - 3).text());
        ret.setTestResult(Checks.requireNonExcept(
                () -> ParseUtil.parseMixedNum(info.get(length - 2).text()),
                0
        ));
        return ret;
    }
}
