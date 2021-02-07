package com.github.romanqed.api.html;

import com.github.romanqed.api.util.Checks;
import com.github.romanqed.api.util.Urls;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pairing extends AbstractHtmlBased {
    private final List<String> characters = new ArrayList<>();

    public Pairing(URL url) {
        this.url = Checks.requireCorrectValue(url, Pairing::validateUrl);
    }

    public Pairing(Element htmlElement) {
        url = Urls.parseAndValidateUrl(htmlElement.attr("href"), Pairing::validateUrl);
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
    protected void fromPage(Document page) {
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
