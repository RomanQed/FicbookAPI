package com.github.romanqed;

import com.github.romanqed.api.html.entities.Fanfic;
import com.github.romanqed.api.loaders.HtmlLoader;

import java.net.MalformedURLException;
import java.net.URL;

public class Main {
    public static void main(String[] args) throws MalformedURLException {
        HtmlLoader loader = new HtmlLoader();
        Fanfic fanfic = loader.asyncLoad(new URL("https://ficbook.net/readfic/10334361"), Fanfic.BUILDER).silent();
        System.out.println(fanfic.tags);
    }
}