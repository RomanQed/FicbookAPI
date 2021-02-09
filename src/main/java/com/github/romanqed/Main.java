package com.github.romanqed;

import com.github.romanqed.api.html.entites.Fanfic;
import com.github.romanqed.api.util.ParseUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Document document = Jsoup.connect("https://ficbook.net/tags/1664").get();
        System.out.println(ParseUtil.entitiesFromPage(document, Fanfic.BUILDER, ParseUtil.FANFIC_QUERY));
        // :comment - атрибут с json для комента
    }
}