package com.github.romanqed.api;

import com.github.romanqed.api.util.Urls;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Pairing extends AbstractHtmlBased {
    private final List<String> characters = new ArrayList<>();

    public Pairing(URL link) {
        if (!validateUrl(link)) {
            throw new IllegalArgumentException("Bad pairing url");
        }
        this.link = link;
    }

    public Pairing(List<String> characters) {
        this.characters.addAll(characters);
        StringBuilder rawLink = new StringBuilder();
        characters.forEach(character -> rawLink.append('/').append(Urls.encodeFicbookUrl(character)));
        link = Urls.attachUrl(Urls.PAIRINGS, rawLink.toString());
    }

    public Pairing(String... characters) {
        this(Arrays.asList(characters));
    }

    public Pairing(Element htmlElement) {
        link = Urls.parseAndValidateUrl(htmlElement.attr("href"), Pairing::validateUrl);
        characters.clear();
        Collections.addAll(characters, htmlElement.text().split("/"));
    }

    public static boolean validateUrl(URL url) {
        return Urls.validateChildUrl(Urls.PAIRINGS, url);
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
    protected void fromPage(String rawPage) {
        Document page = Jsoup.parse(rawPage);
        characters.clear();
        String rawChars = page.selectFirst("h1").text();
        rawChars = rawChars.substring(rawChars.indexOf('"') + 1, rawChars.lastIndexOf('"'));
        Collections.addAll(characters, rawChars.split("/"));
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        ret.append("[Pairing] ");
        characters.forEach(item -> ret.append("[").append(item).append("]"));
        return ret.toString() + " " + super.toString();
    }
}
