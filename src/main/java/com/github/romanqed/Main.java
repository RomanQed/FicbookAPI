package com.github.romanqed;

import com.github.romanqed.api.DataLoader;
import com.github.romanqed.api.html.ChapterLoader;


public class Main {
    public static void main(String[] args) {
        DataLoader<?> loader = new ChapterLoader();
        System.out.println(loader.load("4794800/12403805").silent());
    }
}