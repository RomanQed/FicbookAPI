package com.github.romanqed.api;

import com.github.romanqed.api.interfaces.DefaultHtmlBased;
import org.jsoup.nodes.Element;

public abstract class AbstractDefHtmlBased extends AbstractHtmlBased implements DefaultHtmlBased {
    @Override
    public boolean validateHtml(Element element) {
        return element != null && !element.text().isEmpty();
    }

    @Override
    public void fromHtml(Element element) throws Exception {
        if (!validateHtml(element)) {
            throw new IllegalArgumentException("Bad html element!");
        }
    }
}
