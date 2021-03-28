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

public class BetaFormBuilder implements HtmlPageBuilder<BetaForm> {
    @Override
    public BetaForm build(URL url, Document page) {
        return build(page.selectFirst(Queries.BETA_FORM_QUERY));
    }

    @Override
    public BetaForm build(Element node) {
        Element title = node.selectFirst("div a.title");
        BetaForm ret = new BetaForm(ParseUtil.sliceLastPath(title.attr("href")));
        ret.betaName = title.text();
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
