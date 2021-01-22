package com.github.romanqed.api.interfaces;

import com.github.romanqed.api.util.Urls;
import com.github.romanqed.concurrent.Task;
import com.github.romanqed.concurrent.WebTaskFabric;

import java.net.URL;

public interface Linkable {
    URL getLink();

    Task<? extends Linkable> load(WebTaskFabric taskFabric);

    Task<? extends Linkable> load();

    default void loadNow() {
        load(null).silent();
    }

    boolean fullLoaded();

    boolean validateUrl(URL url);

    default boolean validateUrl(String rawUrl) {
        try {
            return validateUrl(new URL(rawUrl));
        } catch (Exception e) {
            return false;
        }
    }

    default boolean validateLocalUrl(String localUrl) {
        return validateUrl(Urls.parseUrl(localUrl));
    }
}
