package com.github.romanqed.api.html.builders;

import com.github.romanqed.api.html.entities.Tag;
import com.github.romanqed.api.interfaces.HtmlPageBuilder;
import com.github.romanqed.api.util.ParseUtil;
import com.github.romanqed.api.util.Urls;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.URL;

public class TagBuilder implements HtmlPageBuilder<Tag> {
    @Override
    public Tag build(URL url, Document page) {
        Tag ret = new Tag(Urls.sliceUrlLastPath(url));
        String rawTitle = page.selectFirst("h1").text();
        ret.setTitle(rawTitle.substring(rawTitle.indexOf('«') + 1, rawTitle.indexOf('»')));
        ret.setDescription(page.selectFirst("div.well").text());
        return ret;
    }

    @Override
    public Tag build(Element node) {
        Tag ret = new Tag(ParseUtil.sliceLastPath(node.attr("href")));
        ret.setTitle(node.text());
        ret.setDescription(node.attr("title").replaceAll("<.{1,2}>", ""));
        return ret;
    }
}
