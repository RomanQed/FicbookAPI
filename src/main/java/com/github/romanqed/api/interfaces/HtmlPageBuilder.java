package com.github.romanqed.api.interfaces;

import org.jsoup.nodes.Document;

import java.net.URL;

public interface HtmlPageBuilder<T extends Loadable> extends HtmlBuilder<T> {
    T build(URL url, Document page);
}
