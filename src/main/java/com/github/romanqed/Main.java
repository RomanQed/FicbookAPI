package com.github.romanqed;

import com.github.romanqed.api.html.CollectionLoader;
import com.github.romanqed.api.html.PageableCollection;
import com.github.romanqed.api.html.entites.Fandom;
import com.github.romanqed.api.util.Queries;
import com.github.romanqed.api.util.Urls;

import java.net.URL;

public class Main {
    public static void main(String[] args) {
        CollectionLoader loader = new CollectionLoader();
        loader.setBuilder(Fandom.BUILDER);
        loader.setSelector(Queries.FANDOM_QUERY);
        URL url = Urls.attachUrl(Urls.FANDOMS, "books");
        PageableCollection collection = new PageableCollection(loader, url);
        System.out.println(collection.page(2).silent());
    }
}