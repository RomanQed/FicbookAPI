package com.github.romanqed.api.interfaces;

import com.github.romanqed.api.html.AbstractHtmlBased;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.URL;

public interface HtmlBuilder<T extends AbstractHtmlBased> {
    T build(URL url, Document page);

    T build(Element node);
}
