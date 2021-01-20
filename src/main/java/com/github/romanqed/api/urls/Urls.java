package com.github.romanqed.api.urls;

import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class Urls {
    public static final URL MAIN_PAGE = parseUrl("https://ficbook.net");
    public static final URL FANDOMS = parseUrl("fanfiction");
    public static final URL AUTHORS = parseUrl("authors");
    public static final URL POPULAR = parseUrl("popular");
    public static final URL REQUESTS = parseUrl("requests");
    public static final URL BETAS = parseUrl("betas");
    public static final URL FANFIC = parseUrl("readfic");
    public static final URL TAGS = parseUrl("tags");
    public static final URL COLLECTIONS = parseUrl("collections");
    public static final URL FIND = parseUrl("find");
    public static final URL PAIRINGS = parseUrl("pairings");

    public static URL parseUrl(URL context, String rawUrl) {
        try {
            return new URL(context, rawUrl);
        } catch (Exception e) {
            return null;
        }
    }

    public static URL parseUrl(String rawUrl) {
        return parseUrl(MAIN_PAGE, rawUrl);
    }

    public static URL attachUrl(URL context, String rawUrl) {
        try {
            String newUrl = context.toString();
            if (!newUrl.endsWith("/")) {
                newUrl += "/";
            }
            if (rawUrl.startsWith("/")) {
                rawUrl = rawUrl.replaceFirst("/", "");
            }
            return new URL(newUrl + rawUrl);
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean validateChildUrl(URL parent, URL child) {
        return child.getPath().startsWith(parent.getPath());
    }

    public static boolean validateChildUrl(URL parent, String child) {
        try {
            return validateChildUrl(parent, new URL(child));
        } catch (Exception e) {
            return false;
        }
    }

    public static String encodePairing(String rawPairing) {
        try {
            return URLEncoder.encode(rawPairing, StandardCharsets.UTF_8.name()).replace("%2F", "/");
        } catch (Exception e) {
            return "";
        }
    }
}
