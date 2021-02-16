package com.github.romanqed.api.collections;

import com.github.romanqed.api.interfaces.EntityCollectionBuilder;
import com.github.romanqed.api.interfaces.HtmlBuilder;
import com.github.romanqed.api.util.Checks;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

public class HtmlCollectionBuilder<T> implements EntityCollectionBuilder<T, HtmlCollection<T>> {
    private final String query;
    private final HtmlBuilder<T> builder;

    public HtmlCollectionBuilder(String query, HtmlBuilder<T> builder) {
        this.query = Checks.requireNonNullString(query);
        this.builder = builder;
    }

    @Override
    public HtmlCollection<T> build(Document page) {
        Elements elements = page.select(query);
        HtmlCollection<T> ret = new HtmlCollection<>();
        List<T> parsed = ret.getItems();
        for (Element element : elements) {
            parsed.add(builder.build(element));
        }
        return ret;
    }
}
