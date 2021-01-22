package com.github.romanqed.api.html;

import com.github.romanqed.api.AbstractLinkable;
import com.github.romanqed.api.interfaces.HtmlBased;
import com.github.romanqed.concurrent.Task;
import com.github.romanqed.concurrent.WebTaskFabric;

public abstract class AbstractHtmlBased extends AbstractLinkable implements HtmlBased {
    @Override
    public Task<AbstractHtmlBased> load(WebTaskFabric taskFabric) {
        // TODO
        return null;
    }

    @Override
    public Task<AbstractHtmlBased> load() {
        // TODO
        return load(null);
    }
}
