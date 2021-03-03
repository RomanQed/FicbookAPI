package com.github.romanqed;

import com.github.romanqed.api.html.entities.Fanfic;
import com.github.romanqed.api.loaders.HtmlLoader;

import java.net.MalformedURLException;
import java.net.URL;

public class Main {
    public static void main(String[] args) throws MalformedURLException {
        HtmlLoader loader = new HtmlLoader();
        Fanfic ret = loader.asyncLoad(new URL("https://ficbook.net/readfic/10015087"), Fanfic.BUILDER).silent();
        System.out.println(ret.getCover());
    }
}