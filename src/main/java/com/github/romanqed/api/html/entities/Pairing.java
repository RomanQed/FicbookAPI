package com.github.romanqed.api.html.entities;

import com.github.romanqed.api.html.builders.PairingBuilder;
import com.github.romanqed.api.interfaces.HtmlBuilder;
import com.github.romanqed.api.util.Checks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pairing {
    public static final HtmlBuilder<Pairing> BUILDER = new PairingBuilder();
    private final List<String> characters = new ArrayList<>();

    public Pairing(String... characters) {
        Checks.requireCorrectValue(characters, arr -> characters.length != 0);
        Collections.addAll(this.characters, characters);
    }

    public Pairing(String rawCharacters) {
        this(rawCharacters.split("/"));
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
        return ret.toString();
    }
}
