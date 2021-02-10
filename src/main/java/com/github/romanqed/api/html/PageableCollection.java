package com.github.romanqed.api.html;

import com.github.romanqed.api.util.Checks;
import com.github.romanqed.api.util.ParseUtil;
import com.github.romanqed.api.util.Urls;
import com.github.romanqed.concurrent.Task;

import java.net.URL;
import java.util.Collections;
import java.util.List;

public class PageableCollection {
    private final CollectionLoader loader;
    private final URL url;
    private int maxPage = -1;

    public PageableCollection(CollectionLoader loader, URL url) {
        this.loader = Checks.requireNonNullElse(loader, new CollectionLoader());
        this.url = Checks.requireNonNullElse(url, Urls.POPULAR);
    }

    public Task<List<?>> page(int page) {
        if (page < 1) {
            page = 1;
        } else if (page > maxPage && maxPage != -1) {
            page = maxPage;
        }
        loader.onParse(document -> {
            maxPage = Checks.requireNonExcept(
                    () -> Integer.parseInt(document.select(ParseUtil.PAGE_COUNT_QUERY).last().text()),
                    1
            );
        });
        return loader.load(url, Collections.singletonMap("p", Integer.toString(page)));
    }

    public int getMaxPage() {
        return maxPage;
    }
}
