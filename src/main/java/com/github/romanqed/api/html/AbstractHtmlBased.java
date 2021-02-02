package com.github.romanqed.api.html;

import com.github.romanqed.api.AbstractLinkable;

public abstract class AbstractHtmlBased extends AbstractLinkable {
    protected abstract void fromPage(String rawPage);
}
