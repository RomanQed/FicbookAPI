package com.github.romanqed.api.collections;

import com.github.romanqed.api.interfaces.EntityCollectionBuilder;
import com.github.romanqed.api.interfaces.HtmlBuilder;
import com.github.romanqed.api.util.Checks;
import com.github.romanqed.api.util.Queries;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

public class PageableHtmlCollectionBuilder<T> implements EntityCollectionBuilder<T, PageableHtmlCollection<T>> {
    private final String query;
    private final HtmlBuilder<T> builder;

    public PageableHtmlCollectionBuilder(String query, HtmlBuilder<T> builder) {
        this.query = Checks.requireNonNullString(query);
        this.builder = builder;
    }

    @Override
    public PageableHtmlCollection<T> build(Document page) {
        int maxPage = Checks.requireNonExcept(
                () -> Integer.parseInt(page.select(Queries.PAGE_COUNT_QUERY).last().text()),
                -1
        );
        Elements elements = page.select(query);
        PageableHtmlCollection<T> ret = new PageableHtmlCollection<>(maxPage);
        List<T> parsed = ret.getItems();
        for (Element element : elements) {
            parsed.add(builder.build(element));
        }
        return ret;
    }
}
