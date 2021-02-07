package com.github.romanqed.api.html.entites;

import com.github.romanqed.api.html.AbstractHtmlBased;
import com.github.romanqed.api.util.Checks;
import com.github.romanqed.api.util.Urls;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pairing extends AbstractHtmlBased {
    public static final AbstractHtmlBuilder<Pairing> BUILDER = new PairingBuilder();
    private final List<String> characters;

    public Pairing(URL url) {
        this.url = Checks.requireCorrectValue(url, Pairing::validateUrl);
        characters = new ArrayList<>();
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
    public String toString() {
        StringBuilder ret = new StringBuilder();
        ret.append("[Pairing] ");
        characters.forEach(item -> ret.append("[").append(item).append("]"));
        return ret.toString() + " " + super.toString();
    }

    public static class PairingBuilder extends AbstractHtmlBuilder<Pairing> {
        @Override
        public Pairing build(URL url, Document page) {
            Pairing ret = new Pairing(url);
            String rawChars = page.selectFirst("h1").text();
            rawChars = rawChars.substring(rawChars.indexOf('"') + 1, rawChars.lastIndexOf('"'));
            Collections.addAll(ret.characters, rawChars.split("/"));
            return ret;
        }

        @Override
        public Pairing build(Element node) {
            Pairing ret = new Pairing(Urls.parseFicbookUrl(node.attr("href")));
            Collections.addAll(ret.characters, node.text().split("/"));
            return ret;
        }
    }
}
