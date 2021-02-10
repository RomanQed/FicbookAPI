package com.github.romanqed;

import com.github.romanqed.api.html.CollectionLoader;
import com.github.romanqed.api.html.PostCollectionLoader;
import com.github.romanqed.api.json.Comment;
import com.github.romanqed.api.util.ParseUtil;
import com.github.romanqed.api.util.Urls;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Main {
    public static void main(String[] args) throws IOException {
        CollectionLoader loader = new PostCollectionLoader();
        loader.setSelector(ParseUtil.COMMENT_QUERY);
        loader.setBuilder(Comment.BUILDER);
        Map<String, String> a = new ConcurrentHashMap<>();
        a.put("id", "3965460");
        a.put("page", "1");
        System.out.println(loader.load(Urls.parseFicbookUrl("comments/get_fanfic_part_comments"), a).checked(Throwable::printStackTrace).size());
    }
}