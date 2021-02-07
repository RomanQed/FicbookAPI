package com.github.romanqed.api.html;

import com.github.romanqed.api.states.Direction;
import com.github.romanqed.api.states.Rating;
import com.github.romanqed.api.util.Urls;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.URL;
import java.util.*;

public class Request extends AbstractHtmlBased {
    private final Set<Fandom> fandoms = new HashSet<>();
    private final List<Pairing> characters = new ArrayList<>();
    private final Set<Direction> directions = new HashSet<>();
    private final Set<Rating> ratings = new HashSet<>();
    private final Set<Tag> tags = new HashSet<>();
    private String title;
    private User author;
    private int likes;
    private int works;
    private int bookmarks;
    private String description;
    private String hiddenDescription;
    private Date creationDate;

    public Request(URL url) {
        // TODO
    }

    public Request(Element htmlElement) {
        // TODO
    }

    public static boolean validateUrl(URL url) {
        return Urls.validateChildUrl(Urls.REQUESTS, url);
    }

    public String getTitle() {
        return title;
    }

    public User getAuthor() {
        return author;
    }

    public int getLikes() {
        return likes;
    }

    public int getWorks() {
        return works;
    }

    public int getBookmarks() {
        return bookmarks;
    }

    public Set<Fandom> getFandoms() {
        return fandoms;
    }

    public List<Pairing> getCharacters() {
        return characters;
    }

    public Set<Direction> getDirections() {
        return directions;
    }

    public Set<Rating> getRatings() {
        return ratings;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public String getDescription() {
        return description;
    }

    public String getHiddenDescription() {
        return hiddenDescription;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    protected void fromPage(Document page) {
//        // Название заявки
//        System.out.println(requestContent.selectFirst("h1.mb-0").text());
//        // Автор заявки
//        Element author = requestContent.selectFirst("a.avatar-nickname");
//        System.out.println("Ссылка: " + author.attr("href") + " Ник: " + author.text());
//        // Лайки
//        Element likes = requestContent.selectFirst("span.request-like-cnt-number");
//        System.out.println("Лайки: " + likes.text());
//        // Работы
//        Element works = requestContent.selectFirst("a span.int-count");
//        System.out.println("Кол-во работ: " + works.text());
//        // Закладки
//        Element bookmarks = requestContent.selectFirst("span.request-favourite-cnt-number");
//        System.out.println("Закладки: " + bookmarks.text());
//        // Желаемые характеристики
//        Elements metaInfo = requestContent.select("div section div");
//        // Фэндомы
//        Elements fandoms = metaInfo.get(0).getElementsByTag("a");
//        fandoms.forEach(fandom -> {
//            System.out.println("Ссылка: " + fandom.attr("href") + " Название: " + fandom.text());
//        });
//        int corr = metaInfo.size() == 5 ? 1 : 0;
//        // Персонажи
//        if (corr != 0) {
//            System.out.println(metaInfo.get(1).text());
//        }
//        // Направленности
//        Elements genres = metaInfo.get(1 + corr).select("span.help");
//        genres.forEach(genre -> {
//            System.out.println("Жанр: " + genre.attr("title").replaceAll("<.{1,2}>", ""));
//        });
//        // Рейтинг
//        Elements ratings = metaInfo.get(2 + corr).select("span.help");
//        ratings.forEach(rating -> {
//            System.out.printf("Название: %s Описание: %s%n", rating.text(), rating.attr("title").replaceAll("<.{1,2}>", ""));
//        });
//        // Теги
//        Elements tags = metaInfo.select("div.tags a");
//        tags.forEach(tag -> {
//            System.out.println("<--->");
//            System.out.println("Ссылка: " + tag.attr("href"));
//            System.out.println("Описание: " + tag.text().replaceAll("<.{1,2}>", ""));
//            System.out.println("<--->");
//        });
//        // Описание
//        Element description = requestContent.selectFirst("div.word-break");
//        System.out.println("Описание: " + description.wholeText());
//        // Дополнительное описание
//        Element addonDescription = requestContent.selectFirst("div.word-break[style=\"display: none\"]");
//        if (addonDescription != null) {
//            System.out.println("Дополнительное описание: " + addonDescription.wholeText());
//        }
//        // Дата создания
//        Element data = requestContent.selectFirst("p span");
//        System.out.println("Дата создания: " + data.attr("title"));
    }
}
