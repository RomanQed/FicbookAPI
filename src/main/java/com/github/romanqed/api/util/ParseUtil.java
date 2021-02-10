package com.github.romanqed.api.util;

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
    public static final DateFormat jsonDateFormat = new SimpleDateFormat("y-M-d H:m:s.SX");
    public static final DateFormat nativeDateFormat = new SimpleDateFormat("d MMMM y, H:m");
    public static final String FANFIC_QUERY = "article.block";
    public static final String REQUEST_QUERY = "article.request-thumb";
    public static final String COMMENT_QUERY = "article.comment-container";
    public static final String BETA_FORM_QUERY = "div.beta_thumb";

    public static String safetyText(String rawText) {
        return rawText.toLowerCase().replaceAll("\\s", "");
    }

    public static Integer parseMixedNum(String rawNum, int base) {
        return Integer.parseInt(rawNum.replaceAll("\\D", ""), base);
    }

    public static Integer parseMixedNum(String rawNum) {
        return parseMixedNum(rawNum, 10);
    }

    public static Integer checkedMixedNum(String rawNum) {
        return Checks.requireNonExcept(() -> parseMixedNum(rawNum), 0);
    }

    public static Integer parseInt(String rawNum) {
        return Checks.requireNonExcept(() -> Integer.parseInt(rawNum), 0);
    }

    public static Date parseStringDate(String rawDate, DateFormat dateFormat) {
        try {
            return dateFormat.parse(rawDate);
        } catch (Exception e) {
            return new Date();
        }
    }

    public static Date parseNativeDate(String rawDate) {
        return parseStringDate(rawDate, nativeDateFormat);
    }

    public static Date parseJsonDate(String rawDate) {
        return parseStringDate(rawDate, jsonDateFormat);
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
}
