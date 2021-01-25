package com.github.romanqed;

import com.github.romanqed.api.Fandom;
import com.github.romanqed.api.Pairing;
import com.github.romanqed.api.Tag;
import com.github.romanqed.api.util.Urls;
import com.github.romanqed.concurrent.BaseTaskFabric;
import com.github.romanqed.concurrent.Task;
import okhttp3.OkHttpClient;
import okhttp3.Response;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
//        Document doc = Jsoup.connect("https://ficbook.net/readfic/8989993").get();
//        Elements articles = doc.select("article.block");
//        Element article = articles.first();
//        Backup.parseFanficPage(doc);
//        OkHttpClient client = new OkHttpClient().newBuilder()
//                .build();
//        MediaType mediaType = MediaType.parse("text/plain");
//        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
//                .addFormDataPart("id","24126932")
//                .addFormDataPart("page","1")
//                .build();
//        Request request = new Request.Builder()
//                .url("https://ficbook.net/comments/get_fanfic_part_comments")
//                .method("POST", body)
//                .addHeader("referer", "https://ficbook.net/readfic/9395584/24126932")
//                .addHeader("Cookie", "__cfduid=dcfd8ddc970cb70c00626b9c277748f531610241019")
//                .build();
//        Response response = client.newCall(request).execute();
//        Backup.parseReviews(Jsoup.parse(response.body().string()));
//        Document document = Jsoup.connect("https://ficbook.net/readfic/9395584/comments").get();
//        Backup.parseReviews(document);
    }
}