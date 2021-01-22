package com.github.romanqed.api.html;

import com.github.romanqed.api.util.Urls;
import org.jsoup.nodes.Element;

import java.net.URL;

public class Tag extends AbstractHtmlBased {
    private String title = "";
    private String description = "";

    protected Tag(URL checkedLink) {
        link = checkedLink;
    }

    public Tag(Element htmlElement) throws Exception {
        fromHtml(htmlElement);
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public void fromHtml(Element element) {
        link = Urls.parseAndValidateUrl(element.attr("href"), this::validateUrl);
        title = element.text();
        description = element.attr("title").replaceAll("<.{1,2}>", "");
    }

    @Override
    public boolean fullLoaded() {
        return !(title.isEmpty() || description.isEmpty());
    }

    @Override
    public boolean validateUrl(URL url) {
        return Urls.validateChildUrl(Urls.TAGS, url);
    }

    @Override
    public String toString() {
        return "[Tag] " + title + " " + super.toString();
    }
}
