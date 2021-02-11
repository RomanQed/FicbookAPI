package com.github.romanqed.api.html;

import com.github.romanqed.api.html.loaders.CollectionLoader;
import com.github.romanqed.api.util.Checks;
import com.github.romanqed.api.util.Pair;
import com.github.romanqed.api.util.Queries;
import com.github.romanqed.api.util.Urls;
import com.github.romanqed.concurrent.Task;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class PageableCollection {
    private final CollectionLoader loader;
    private final URL url;
    private int maxPage = -1;

    public PageableCollection(CollectionLoader loader, URL url) {
        this.loader = Checks.requireCorrectValue(loader, Objects::nonNull);
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
                    () -> Integer.parseInt(document.select(Queries.PAGE_COUNT_QUERY).last().text()),
                    -1
            );
        });
        return loader.load(url, Collections.singletonList(new Pair<>("p", Integer.toString(page))));
    }

    public int getMaxPage() {
        return maxPage;
    }
}
