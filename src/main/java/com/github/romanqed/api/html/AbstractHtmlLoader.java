package com.github.romanqed.api.html;

import com.github.romanqed.api.util.Checks;
import com.github.romanqed.concurrent.TaskFabric;
import kong.unirest.UnirestInstance;
import org.jsoup.Jsoup;

import java.net.URL;

public abstract class AbstractHtmlLoader<T extends AbstractHtmlBased> extends AbstractLinkableLoader<T> {
    public AbstractHtmlLoader(UnirestInstance client, TaskFabric taskFabric) {
        super(client, taskFabric);
    }

    @Override
    protected T fromResponse(URL url, String body) {
        Class<T> tClass = getElementClass();
        T ret = Checks.requireNonExcept(() -> tClass.getDeclaredConstructor(URL.class).newInstance(url), null);
        if (ret == null) {
            return null;
        }
        ret.fromPage(Jsoup.parse(body));
        return ret;
    }

    protected abstract Class<T> getElementClass();
}
