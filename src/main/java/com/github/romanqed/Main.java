package com.github.romanqed;

import com.github.romanqed.api.html.entites.BetaForm;
import com.github.romanqed.api.util.ParseUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Document document = Jsoup.connect("https://ficbook.net/betas").get();
        System.out.println(ParseUtil.entitiesFromPage(document, BetaForm.BUILDER, ParseUtil.BETA_FORM_QUERY));
        // :comment - атрибут с json для комента
    }
}