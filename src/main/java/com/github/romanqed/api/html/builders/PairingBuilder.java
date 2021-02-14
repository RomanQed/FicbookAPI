package com.github.romanqed.api.html.builders;

import com.github.romanqed.api.html.entities.Pairing;
import com.github.romanqed.api.interfaces.HtmlBuilder;
import org.jsoup.nodes.Element;

public class PairingBuilder implements HtmlBuilder<Pairing> {
    @Override
    public Pairing build(Element node) {
        return new Pairing(node.text());
    }
}
