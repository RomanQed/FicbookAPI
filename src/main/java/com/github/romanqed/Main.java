package com.github.romanqed;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect("https://ficbook.net/readfic/8989993").get();
        Elements articles = doc.select("article.block");
        Element article = articles.first();
        Backup.parseFanficPage(doc);
    }
}