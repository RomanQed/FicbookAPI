package com.github.romanqed.api.html;

import com.github.romanqed.api.util.Urls;
import org.jsoup.nodes.Document;
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

    public Pairing(List<String> characters) {
        // TODO
    }

    public Pairing(Element htmlElement) {
        link = Urls.parseAndValidateUrl(htmlElement.attr("href"), this::validateUrl);
        characters.clear();
        Collections.addAll(characters, htmlElement.text().split("/"));
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
    public String toString() {
        StringBuilder ret = new StringBuilder();
        ret.append("[Pairing] ");
        characters.forEach(item -> ret.append("[").append(item).append("]"));
        return ret.toString() + " " + super.toString();
    }

    @Override
    protected void fromPage(Document document) {
        // TODO
    }
}
