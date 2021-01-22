package com.github.romanqed.api.html;

import com.github.romanqed.api.util.Urls;
import org.jsoup.nodes.Element;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pairing extends AbstractHtmlBased {
    private final List<String> characters = new ArrayList<>();

    protected Pairing(URL checkedLink) {
        link = checkedLink;
    }

    public Pairing(Element htmlElement) throws Exception {
        fromHtml(htmlElement);
    }

    public List<String> getCharacters() {
        return characters;
    }

    public boolean isSingle() {
        return characters.size() == 1;
    }

    @Override
    public boolean fullLoaded() {
        return characters.stream().anyMatch(item -> !item.isEmpty());
    }

    @Override
    public boolean validateUrl(URL url) {
        return Urls.validateChildUrl(Urls.PAIRINGS, url);
    }

    @Override
    public void fromHtml(Element element) {
        link = Urls.parseAndValidateUrl(element.attr("href"), this::validateUrl);
        characters.clear();
        Collections.addAll(characters, element.text().split("/"));
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        ret.append("[Pairing] ");
        characters.forEach(item -> ret.append("[").append(item).append("]"));
        return ret.toString() + " " + super.toString();
    }
}
