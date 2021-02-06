package com.github.romanqed;

import kong.unirest.HttpRequest;
import kong.unirest.Unirest;
import kong.unirest.UnirestInstance;

public class Main {
    public static void main(String[] args) {
        UnirestInstance instance = Unirest.spawnInstance();
        HttpRequest<?> a = instance.get("https://ficbook.net");
        System.out.println(a.asString().getBody());
    }
}