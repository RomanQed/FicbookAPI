package com.github.romanqed;

import com.github.romanqed.api.collections.builders.PageableCollectionBuilder;
import com.github.romanqed.api.html.entities.Fanfic;
import com.github.romanqed.api.interfaces.CollectionBuilder;
import com.github.romanqed.api.loaders.CollectionLoader;
import com.github.romanqed.api.util.Pair;
import com.github.romanqed.api.util.Queries;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) throws MalformedURLException {
        CollectionBuilder<Fanfic> builder = new PageableCollectionBuilder<>(Queries.FANFIC_QUERY, Fanfic.BUILDER);
        CollectionLoader loader = new CollectionLoader();
        Pair<Integer, List<Fanfic>> answer = loader.asyncLoad(
                new URL("https://ficbook.net/tags/1668"),
                Collections.singletonList(new Pair<>("p", "2")),
                builder
        ).silent();
        System.out.println(answer.getKey());
    }
}