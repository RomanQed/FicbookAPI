package com.github.romanqed.api.collections.builders;

import com.github.romanqed.api.interfaces.HtmlBuilder;
import com.github.romanqed.api.util.Pair;
import com.github.romanqed.api.util.ParseUtil;
import org.jsoup.nodes.Document;

import java.util.List;

public class BaseCollectionBuilder<T> extends AbstractCollectionBuilder<T> {
    public BaseCollectionBuilder(String query, HtmlBuilder<T> builder) {
        super(query, builder);
    }

    @Override
    public Pair<Integer, List<T>> build(Document page) {
        return new Pair<>(-1, ParseUtil.entitiesFromPage(page, builder, query));
    }
}
