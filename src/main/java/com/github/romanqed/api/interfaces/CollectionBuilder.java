package com.github.romanqed.api.interfaces;

import com.github.romanqed.api.util.Pair;
import org.jsoup.nodes.Document;

import java.util.List;

public interface CollectionBuilder<T> {
    Pair<Integer, List<T>> build(Document page);
}
