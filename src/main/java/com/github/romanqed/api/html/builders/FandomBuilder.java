package com.github.romanqed.api.html.builders;

import com.github.romanqed.api.html.entities.Fandom;
import com.github.romanqed.api.interfaces.HtmlPageBuilder;
import com.github.romanqed.api.util.ParseUtil;
import com.github.romanqed.api.util.Urls;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.URL;

public class FandomBuilder implements HtmlPageBuilder<Fandom> {
    @Override
    public Fandom build(URL url, Document page) {
        Fandom ret = new Fandom(Urls.slicePath(Urls.FANDOMS, url));
        Element title = page.selectFirst("h1");
        String rawTitle = title.text();
        ret.title = rawTitle.substring(rawTitle.indexOf('«') + 1, rawTitle.indexOf('»'));
        ret.fictionId = ParseUtil.parseMixedNum(title.selectFirst("a").attr("href"));
        return ret;
    }

    @Override
    public Fandom build(Element node) {
        Fandom ret = new Fandom(node.attr("href").replace("/fanfiction/", ""));
        ret.title = node.text().trim();
        return ret;
    }
}
