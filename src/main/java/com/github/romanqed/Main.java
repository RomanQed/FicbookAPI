package com.github.romanqed;

import com.github.romanqed.api.html.entites.Fanfic;
import com.github.romanqed.api.html.entites.Request;
import com.github.romanqed.api.html.loaders.RequestLoader;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {
    public static void main(String[] args) throws IOException {
        AtomicBoolean b = new AtomicBoolean(true);
        RequestLoader loader = new RequestLoader();
        for (int i = 1; i < 10000; ++i) {
            System.out.println(loader.load(i).checked(e -> b.set(e instanceof IOException)));
            if (!b.get()) {
                System.out.println("PIZDEC) " + i);
                break;
            }
        }
    }

    public static void printAllRequestInfo(Request request) {
        System.out.println(request.getTitle());
        System.out.println(request.getFandoms());
        System.out.println(request.getDirections());
        System.out.println(request.getRatings());
        System.out.println(request.isPremium());
        System.out.println(request.getLikes());
        System.out.println(request.getWorks());
        System.out.println(request.getBookmarks());
        System.out.println(request.getAuthor());
        System.out.println(request.getCharacters());
        System.out.println(request.getTags());
        System.out.println(request.getDescription());
        System.out.println(request.getHiddenDescription());
        System.out.println(request.getCreationDate());
    }

    public static void printAllFanficInfo(Fanfic fanfic) {
        System.out.println(fanfic.getTitle());
        System.out.println(fanfic.getFandoms());
        System.out.println(fanfic.getDirection());
        System.out.println(fanfic.getRating());
        System.out.println(fanfic.isTranslate());
        if (fanfic.isTranslate()) {
            System.out.println(fanfic.getOriginalAuthor());
            System.out.println(fanfic.getOriginalFanfic());
        }
        System.out.println(fanfic.isPremium());
        System.out.println(fanfic.getStatus());
        System.out.println(fanfic.getLikes());
        System.out.println(fanfic.inCollections());
        System.out.println(fanfic.getAuthors());
        System.out.println(fanfic.getPairings());
        System.out.println(fanfic.getSize());
        System.out.println(fanfic.getActualPages());
        System.out.println(fanfic.getTags());
        System.out.println(fanfic.getDescription());
        System.out.println(fanfic.getDedication());
        System.out.println(fanfic.getNotes());
        System.out.println(fanfic.getCopyright());
        System.out.println(fanfic.getRewards());
        System.out.println(fanfic.getChapters());
    }
}