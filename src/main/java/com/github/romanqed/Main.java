package com.github.romanqed;

import com.github.romanqed.api.html.Fanfic;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect("https://ficbook.net/authors/14939/profile/works").get();
        Element article = doc.selectFirst("article.block");
        Fanfic fanfic = new Fanfic(article);
        System.out.println(fanfic.getDescription());
    }
}