package com.github.romanqed.api.html;

import com.github.romanqed.api.AbstractLinkable;
import com.github.romanqed.api.interfaces.HtmlBased;
import org.jsoup.nodes.Element;

import java.net.URL;

public abstract class User extends AbstractLinkable implements HtmlBased {

    @Override
    public void fromHtml(Element element) {

    }

    @Override
    public boolean fullLoaded() {
        return false;
    }

    @Override
    public boolean validateUrl(URL url) {
        return false;
    }
}
