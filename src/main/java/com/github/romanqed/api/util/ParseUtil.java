package com.github.romanqed.api.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class ParseUtil {
    public static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSSX", Locale.ENGLISH);

    public static String safetyText(String rawText) {
        return rawText.toLowerCase().replaceAll("\\s", "");
    }

    public static Integer parseMixedNum(String rawNum, int base) {
        return Integer.parseInt(rawNum.replaceAll("\\D", ""), base);
    }

    public static Integer parseMixedNum(String rawNum) {
        return parseMixedNum(rawNum, 10);
    }
}
