package com.github.romanqed;

import com.github.romanqed.api.json.Comment;
import com.github.romanqed.api.util.ParseUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        Document document = Jsoup.connect("https://ficbook.net/readfic/6023919/comments#content").get();
        List<Comment> commentList = ParseUtil.entitiesFromPage(document, Comment.BUILDER, ParseUtil.COMMENT_QUERY);
        commentList.forEach(System.out::println);
//        CollectionLoader loader = new CollectionLoader();
//        loader.setSelector(ParseUtil.COMMENT_QUERY);
//        loader.setBuilder(Comment.);
    }
}