package com.github.romanqed.api.html;

import com.github.romanqed.api.AbstractLinkable;
import org.jsoup.nodes.Document;

public abstract class AbstractHtmlBased extends AbstractLinkable {
    protected abstract void fromPage(Document page);
}
