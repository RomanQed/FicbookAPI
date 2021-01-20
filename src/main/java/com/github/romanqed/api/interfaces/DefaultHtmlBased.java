package com.github.romanqed.api.interfaces;

import org.jsoup.nodes.Element;

public interface DefaultHtmlBased extends HtmlBased {
    boolean validateHtml(Element element);
}
