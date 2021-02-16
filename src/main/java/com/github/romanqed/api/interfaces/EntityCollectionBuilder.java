package com.github.romanqed.api.interfaces;

import org.jsoup.nodes.Document;

public interface EntityCollectionBuilder<E, T extends EntityCollection<E>> {
    T build(Document page);
}
