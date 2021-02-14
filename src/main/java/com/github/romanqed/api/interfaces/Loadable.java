package com.github.romanqed.api.interfaces;

import java.net.URL;

public interface Loadable {
    URL getUrl();

    HtmlPageBuilder<?> getBuilder();
}
