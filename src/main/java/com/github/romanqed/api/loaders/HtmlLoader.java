package com.github.romanqed.api.loaders;

import com.github.romanqed.api.interfaces.HtmlPageBuilder;
import com.github.romanqed.api.interfaces.Loadable;
import com.github.romanqed.api.interfaces.lambdas.ResponseProcessor;
import com.github.romanqed.concurrent.Task;
import com.github.romanqed.concurrent.TaskFabric;
import kong.unirest.UnirestInstance;
import org.jsoup.Jsoup;

import java.net.URL;

public class HtmlLoader extends AsyncLoader {
    public HtmlLoader(UnirestInstance client, TaskFabric fabric) {
        super(client, fabric);
    }

    public HtmlLoader(UnirestInstance client) {
        this(client, null);
    }

    public HtmlLoader() {
        this(null, null);
    }

    public <T extends Loadable> Task<T> asyncLoad(URL url, HtmlPageBuilder<T> builder) {
        ResponseProcessor<T> processor = (u, b) -> builder.build(url, Jsoup.parse(b));
        return asyncLoad(url, null, processor);
    }

    @SuppressWarnings("unchecked")
    public <T extends Loadable> Task<T> asyncLoad(T prototype) {
        try {
            return (Task<T>) asyncLoad(prototype.getUrl(), prototype.getBuilder());
        } catch (Exception e) {
            return null;
        }
    }
}
