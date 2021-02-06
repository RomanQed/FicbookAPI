package com.github.romanqed;

import com.github.romanqed.api.html.UserLoader;
import kong.unirest.HttpRequest;
import kong.unirest.Unirest;
import kong.unirest.UnirestInstance;

public class Main {
    public static void main(String[] args) {
        UserLoader loader = new UserLoader();
        System.out.println(loader.load(5).silent());
    }
}