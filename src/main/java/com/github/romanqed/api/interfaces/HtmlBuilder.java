package com.github.romanqed.api.interfaces;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.URL;

public interface HtmlBuilder<T> {
    T build(URL url, Document page);

    T build(Element node);
}
