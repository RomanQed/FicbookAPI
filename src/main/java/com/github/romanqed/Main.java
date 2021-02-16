package com.github.romanqed;

import com.github.romanqed.api.html.entities.Fanfic;

import java.net.MalformedURLException;

public class Main {
    public static void main(String[] args) {
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