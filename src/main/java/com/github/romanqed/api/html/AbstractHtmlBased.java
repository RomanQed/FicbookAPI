package com.github.romanqed.api.html;

import com.github.romanqed.api.AbstractLinkable;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.URL;

public abstract class AbstractHtmlBased extends AbstractLinkable {
    public abstract static class AbstractHtmlBuilder<T extends AbstractHtmlBased> {
        public abstract T build(URL url, Document page);

        public abstract T build(Element node);
    }
}
