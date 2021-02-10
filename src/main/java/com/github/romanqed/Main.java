package com.github.romanqed;

import com.github.romanqed.api.html.HtmlLoader;
import com.github.romanqed.api.html.entites.Fanfic;
import com.github.romanqed.api.util.Urls;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        /*Document document = Jsoup.connect("https://ficbook.net/betas").get();
        System.out.println(ParseUtil.entitiesFromPage(document, BetaForm.BUILDER, ParseUtil.BETA_FORM_QUERY));*/

        // :comment - атрибут с json для комента
        HtmlLoader loader = new HtmlLoader();
        loader.setBuilder(Fanfic.BUILDER);
        Fanfic fanfic = (Fanfic) loader.load(Urls.parseFicbookUrl("readfic/6023919")).silent();
        System.out.println(fanfic);
    }
}