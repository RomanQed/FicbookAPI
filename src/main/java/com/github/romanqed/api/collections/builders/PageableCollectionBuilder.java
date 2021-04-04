package com.github.romanqed.api.collections.builders;

import com.github.romanqed.api.interfaces.HtmlBuilder;
import com.github.romanqed.api.util.Pair;
import com.github.romanqed.api.util.ParseUtil;
import com.github.romanqed.api.util.Queries;
import org.jsoup.nodes.Document;

import java.util.List;

public class PageableCollectionBuilder<T> extends AbstractCollectionBuilder<T> {
    public PageableCollectionBuilder(String query, HtmlBuilder<T> builder) {
        super(query, builder);
    }

    @Override
    public Pair<Integer, List<T>> build(Document page) {
        int pages = ParseUtil.parseInt(page.select(Queries.PAGE_COUNT_QUERY).last().text(), -1);
        return new Pair<>(pages, ParseUtil.entitiesFromPage(page, builder, query));
    }
}
