package com.github.romanqed.api.util;

import com.github.romanqed.api.html.entities.Pairing;
import com.github.romanqed.api.interfaces.HtmlBuilder;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class ParseUtil {
    public static final DateFormat JSON_DATE_FORMAT = new SimpleDateFormat("y-M-d H:m:s.SX");
    public static final DateFormat NATIVE_DATE_FORMAT = new SimpleDateFormat("d MMMM y, H:m");
    public static final DateFormat SEARCH_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public static String safetyText(String rawText) {
        return rawText.toLowerCase().replaceAll("\\s", "");
    }

    public static Integer parseMixedNum(String rawNum, int base) {
        return Integer.parseInt(rawNum.replaceAll("\\D", ""), base);
    }

    public static Integer parseMixedNum(String rawNum) {
        return parseMixedNum(rawNum, 10);
    }

    public static Integer safetyNumber(String rawNum) {
        try {
            return parseMixedNum(rawNum, 10);
        } catch (Exception e) {
            return 0;
        }
    }

    public static Integer checkedMixedNum(String rawNum) {
        return Checks.requireNonExcept(() -> parseMixedNum(rawNum), 0);
    }

    public static Integer parseInt(String rawNum, int def) {
        return Checks.requireNonExcept(() -> Integer.parseInt(rawNum), def);
    }

    public static Integer parseInt(String rawNum) {
        return parseInt(rawNum, 0);
    }

    public static Date parseStringDate(String rawDate, DateFormat dateFormat) {
        try {
            return dateFormat.parse(rawDate);
        } catch (Exception e) {
            return new Date();
        }
    }

    public static Date parseNativeDate(String rawDate) {
        return parseStringDate(rawDate, NATIVE_DATE_FORMAT);
    }

    public static Date parseJsonDate(String rawDate) {
        return parseStringDate(rawDate, JSON_DATE_FORMAT);
    }

    public static <T> void parseHtmlNodes(HtmlBuilder<T> builder, Elements elements, Collection<T> collection) {
        if (elements == null) {
            return;
        }
        for (Element element : elements) {
            collection.add(builder.build(element));
        }
    }

    public static <T> List<T> entitiesFromPage(Document page, HtmlBuilder<T> builder, String query) {
        List<T> ret = new ArrayList<>();
        Elements found = page.select(query);
        for (Element item : found) {
            ret.add(builder.build(item));
        }
        return ret;
    }

    public static String pairingToSearchFormat(Pairing pairing, String delimiter) {
        StringBuilder ret = new StringBuilder();
        ret.append(pairing.characters.get(0));
        for (int i = 1; i < pairing.characters.size(); ++i) {
            ret.append(delimiter).append(pairing.characters.get(i));
        }
        return ret.toString();
    }

    public static String sliceLast(String rawUrl, String delimiter) {
        String[] splitStr = rawUrl.split(delimiter);
        return splitStr[splitStr.length - 1];
    }

    public static String sliceLastPath(String path) {
        return sliceLast(path, "/");
    }
}
