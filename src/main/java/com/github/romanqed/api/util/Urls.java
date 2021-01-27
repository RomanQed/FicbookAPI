package com.github.romanqed.api.util;

import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.function.Predicate;

public class Urls {
    public static final URL MAIN_PAGE = parseFicbookUrl("https://ficbook.net");
    public static final URL FANDOMS = parseFicbookUrl("fanfiction");
    public static final URL AUTHORS = parseFicbookUrl("authors");
    public static final URL POPULAR = parseFicbookUrl("popular");
    public static final URL REQUESTS = parseFicbookUrl("requests");
    public static final URL BETAS = parseFicbookUrl("betas");
    public static final URL FANFIC = parseFicbookUrl("readfic");
    public static final URL TAGS = parseFicbookUrl("tags");
    public static final URL COLLECTIONS = parseFicbookUrl("collections");
    public static final URL FIND = parseFicbookUrl("find");
    public static final URL PAIRINGS = parseFicbookUrl("pairings");
    public static final URL DEFAULT_AVATAR = parseUrl("https://static.ficbook.net/ficbook/design/default_avatar.png");

    public static URL parseUrl(String rawUrl) {
        try {
            return new URL(rawUrl);
        } catch (Exception e) {
            return null;
        }
    }

    public static URL parseFicbookUrl(URL context, String rawUrl) {
        try {
            return new URL(context, rawUrl);
        } catch (Exception e) {
            return null;
        }
    }

    public static URL parseFicbookUrl(String rawUrl) {
        return parseFicbookUrl(MAIN_PAGE, rawUrl);
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

    public static String encodeUrl(String rawUrl) {
        try {
            return URLEncoder.encode(rawUrl, StandardCharsets.UTF_8.name());
        } catch (Exception e) {
            return "";
        }
    }

    public static String encodeFicbookUrl(String rawUrl) {
        return encodeUrl(rawUrl).replace("+", "%20");
    }

    public static String encodePairing(String rawPairing) {
        return encodeUrl(rawPairing).replace("%2F", "/");
    }

    public static URL parseAndValidateUrl(String rawUrl, Predicate<URL> predicate) {
        URL ret = parseFicbookUrl(rawUrl);
        if (ret == null || !predicate.test(ret)) {
            throw new IllegalArgumentException("Bad url!");
        }
        return ret;
    }
}
