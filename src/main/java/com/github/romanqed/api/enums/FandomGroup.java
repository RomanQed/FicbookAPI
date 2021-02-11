package com.github.romanqed.api.enums;

import com.github.romanqed.api.util.Urls;

import java.net.URL;

public enum FandomGroup {
    ANIME_AND_MANGA(Urls.attachFandomUrl("anime_and_manga"), 1),
    BOOKS(Urls.attachFandomUrl("books"), 2),
    CARTOONS(Urls.attachFandomUrl("cartoons"), 3),
    GAMES(Urls.attachFandomUrl("games"), 4),
    MOVIES_AND_TV_SERIES(Urls.attachFandomUrl("movies_and_tv_series"), 5),
    OTHER(Urls.attachFandomUrl("other"), 6),
    RPF(Urls.attachFandomUrl("rpf"), 9),
    COMICS(Urls.attachFandomUrl("comics"), 10),
    MUSICAL(Urls.attachFandomUrl("musicals"), 11);

    final URL url;
    final int id;

    FandomGroup(URL url, int id) {
        this.url = url;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public URL getUrl() {
        return url;
    }
}
