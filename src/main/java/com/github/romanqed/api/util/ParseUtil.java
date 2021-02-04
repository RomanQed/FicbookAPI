package com.github.romanqed.api.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ParseUtil {
    public static final DateFormat jsonDateFormat = new SimpleDateFormat("y-M-d H:m:s.SX");
    public static final DateFormat nativeDateFormat = new SimpleDateFormat("d MMMM y, H:m");

    public static String safetyText(String rawText) {
        return rawText.toLowerCase().replaceAll("\\s", "");
    }

    public static Integer parseMixedNum(String rawNum, int base) {
        return Integer.parseInt(rawNum.replaceAll("\\D", ""), base);
    }

    public static Integer parseMixedNum(String rawNum) {
        return parseMixedNum(rawNum, 10);
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
}
