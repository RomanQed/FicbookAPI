package com.github.romanqed.api.html;

import com.github.romanqed.api.util.Urls;
import org.jsoup.nodes.Element;

import java.net.URL;

public class Request extends AbstractHtmlBased {
    public Request(URL url) {
        // TODO
    }

    public Request(Element htmlElement) {
        // TODO
    }

    public static boolean validateUrl(URL url) {
        return Urls.validateChildUrl(Urls.REQUESTS, url);
    }

    @Override
    protected void fromPage(String rawPage) {

    }
}
