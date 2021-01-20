package com.github.romanqed.api.interfaces;

import org.jsoup.nodes.Element;

public interface HtmlBased {
    void fromHtml(Element element) throws Exception;
}
