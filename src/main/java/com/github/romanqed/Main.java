package com.github.romanqed;

import com.github.romanqed.api.html.ChapterLoader;

public class Main {
    public static void main(String[] args) {
        ChapterLoader loader = new ChapterLoader();
        System.out.println(loader.load("/4794800/12403805").silent());
    }
}