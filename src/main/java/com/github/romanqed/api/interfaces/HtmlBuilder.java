package com.github.romanqed.api.interfaces;

import org.jsoup.nodes.Element;

public interface HtmlBuilder<T> {
    T build(Element node);
}
