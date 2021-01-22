package com.github.romanqed;

import com.github.romanqed.api.html.Fandom;
import com.github.romanqed.api.html.Pairing;
import com.github.romanqed.api.json.Comment;
import com.github.romanqed.api.html.Tag;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Backup {
    public static void parseRawRequestPage(Document document) {
        parseRequestPage(document.selectFirst("section.request-content"));
    }

    public static void parseRequestPage(Element requestContent) {
        // Название заявки
        System.out.println(requestContent.selectFirst("h1.mb-0").text());
        // Автор заявки
        Element author = requestContent.selectFirst("a.avatar-nickname");
        System.out.println("Ссылка: " + author.attr("href") + " Ник: " + author.text());
        // Лайки
        Element likes = requestContent.selectFirst("span.request-like-cnt-number");
        System.out.println("Лайки: " + likes.text());
        // Работы
        Element works = requestContent.selectFirst("a span.int-count");
        System.out.println("Кол-во работ: " + works.text());
        // Закладки
        Element bookmarks = requestContent.selectFirst("span.request-favourite-cnt-number");
        System.out.println("Закладки: " + bookmarks.text());
        // Желаемые характеристики
        Elements metaInfo = requestContent.select("div section div");
        // Фэндомы
        Elements fandoms = metaInfo.get(0).getElementsByTag("a");
        fandoms.forEach(fandom -> {
            System.out.println("Ссылка: " + fandom.attr("href") + " Название: " + fandom.text());
        });
        int corr = metaInfo.size() == 5 ? 1 : 0;
        // Персонажи
        if (corr != 0) {
            System.out.println(metaInfo.get(1).text());
        }
        // Направленности
        Elements genres = metaInfo.get(1 + corr).select("span.help");
        genres.forEach(genre -> {
            System.out.println("Жанр: " + genre.attr("title").replaceAll("<.{1,2}>", ""));
        });
        // Рейтинг
        Elements ratings = metaInfo.get(2 + corr).select("span.help");
        ratings.forEach(rating -> {
            System.out.printf("Название: %s Описание: %s%n", rating.text(), rating.attr("title").replaceAll("<.{1,2}>", ""));
        });
        // Теги
        Elements tags = metaInfo.select("div.tags a");
        tags.forEach(tag -> {
            System.out.println("<--->");
            System.out.println("Ссылка: " + tag.attr("href"));
            System.out.println("Описание: " + tag.text().replaceAll("<.{1,2}>", ""));
            System.out.println("<--->");
        });
        // Описание
        Element description = requestContent.selectFirst("div.word-break");
        System.out.println("Описание: " + description.wholeText());
        // Дополнительное описание
        Element addonDescription = requestContent.selectFirst("div.word-break[style=\"display: none\"]");
        if (addonDescription != null) {
            System.out.println("Дополнительное описание: " + addonDescription.wholeText());
        }
        // Дата создания
        Element data = requestContent.selectFirst("p span");
        System.out.println("Дата создания: " + data.attr("title"));
    }

    public static void parseRequests(Document document) {
        Elements requests = document.select("article.request-thumb");
        requests.forEach(Backup::parseRequestView);
    }

    public static void parseRequestView(Element request) {
        // Head
        // Лайки
        Element likes = request.selectFirst("span.request-likes-counter");
        System.out.println("Лайки: " + likes.text());
        Element bookmarks = request.selectFirst("span.request-bookmark-counter");
        System.out.println("Закладки: " + bookmarks.text());
        Element response = request.selectFirst("span.container-counter");
        System.out.println("Работы по заявке: " + response.text());
        // Body
        // Название
        Element title = request.selectFirst("a.visit-link");
        System.out.println("Ссылка: " + title.attr("href") + " Название: " + title.text());
        // Фэндом
        Elements fandoms = request.select("strong.title a");
        System.out.println("Фэндомы: ");
        fandoms.forEach(fandom -> {
            System.out.println("Ссылка: " + fandom.attr("href") + " Название: " + fandom.text());
        });
        Elements metaInfo = request.select("section.request-description div");
        // Направленности
        Elements genres = metaInfo.get(0).select("span.help");
        genres.forEach(genre -> {
            System.out.println("Жанр: " + genre.attr("title").replaceAll("<.{1,2}>", ""));
        });
        // Рейтинг
        Elements ratings = metaInfo.get(1).select("span.help");
        ratings.forEach(rating -> {
            System.out.printf("Название: %s Описание: %s%n", rating.text(), rating.attr("title").replaceAll("<.{1,2}>", ""));
        });
        // Теги
        Elements tags = request.select("div.tags a");
        tags.forEach(tag -> {
            System.out.println("<--->");
            System.out.println("Ссылка: " + tag.attr("href"));
            System.out.println("Описание: " + tag.text().replaceAll("<.{1,2}>", ""));
            System.out.println("<--->");
        });
        // Описание
        Element description = request.selectFirst("div.post-content");
        System.out.println("Описание: " + description.text());
    }

    // Сделано
    public static void parseReviews(Document document) {
        Elements reviews = document.select("article.comment-container");
        reviews.forEach(Backup::parseReview);
    }

    // Сделано
    public static void parseReview(Element rawReview) {
        Element rawLikes = rawReview.selectFirst("comment-like");
        if (rawLikes == null) {
            return;
        }
        // TODO Не забывать потом чекнуть этот json'чик, много полезной инфы содержит
        JsonObject review = JsonParser.parseString(rawLikes.attr(":comment")).getAsJsonObject();
        try {
            System.out.println(new Comment(review));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Сделано
    public static void parseReward(JsonObject reward) {
        System.out.println("Награда: ");
        String userComment = reward.get("user_text").getAsString();
        System.out.println("Текст награды: " + userComment);
        String date = reward.get("added").getAsString();
        System.out.println("Дата получения: " + date);
        // Может быть битым!!!
        int userId;
        try {
            userId = reward.get("giver_id").getAsInt();
        } catch (Exception e) {
            userId = -1;
        }
        System.out.println("Id наградившего: " + userId);
        String nickname;
        try {
            nickname = reward.get("nickname").getAsString();
        } catch (Exception e) {
            nickname = "битый еблан)";
        }
        System.out.println("Ник юзера: " + nickname);
    }

    public static boolean checkFandomsPage(Document document) {
        return document.selectFirst("section.content-section div.mb-15") == null;
    }

    public static void parseFanficPage(Document doc) {
//        Document doc = Jsoup.connect("https://ficbook.net/readfic/9734952").get();
//        Document doc = Jsoup.connect("https://ficbook.net/readfic/8989993").get();
        Element fanficMainInfo = doc.selectFirst("div.fanfic-main-info");
        // Название
        String title = fanficMainInfo.getElementsByAttributeValue("itemprop", "name").text();
        System.out.println(title);
        // Фэндомы
        System.out.println("Фэндомы");
        Elements fandoms = fanficMainInfo.select("div.mb-10 a");
        fandoms.forEach(fandom -> {
            try {
                System.out.println(new Fandom(fandom));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        // Жанр
        Element genre = fanficMainInfo.selectFirst("div");
        System.out.println("Жанр: " + genre.attr("title").replaceAll("\\s{2,}", " "));
        // Рейтинг
        Element rating = fanficMainInfo.selectFirst("strong");
        System.out.println("Рейтинг: " + rating.attr("title").replaceAll("<.{1,2}>", ""));
        // Статус
        Element status = fanficMainInfo.selectFirst("span span.badge-text");
        System.out.println("Статус: " + status.text());
        // Лайки
        Element likes = fanficMainInfo.selectFirst("span.badge-like");
        System.out.println("Лайков: " + likes.text());


        Element fanficHat = doc.selectFirst("section.fanfic-hat");
        // Авторы
        System.out.println("Авторы: ");
        Elements authors = fanficHat.select("div.hat-creator-container");
        authors.forEach(author -> {
            System.out.println("<--->");
            String info = author.text();
            System.out.println("Имя: " + info.substring(0, info.lastIndexOf(' ')));
            System.out.println("Ссылка: " + author.selectFirst("a").attr("href"));
            System.out.println("Аватар: " + author.selectFirst("img").attr("src"));
            System.out.println("Роль: " + info.substring(info.lastIndexOf(' ') + 1));
            System.out.println("<--->");
        });
        // Персонажи/Пейринги
        Elements pairings = fanficHat.select("a.pairing-link");
        if (!pairings.isEmpty()) {
            System.out.println("Пейринги: ");
        }
        pairings.forEach(pairing -> {
            try {
                System.out.println(new Pairing(pairing));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        // Размер
        Element size = fanficHat.selectFirst("div.mb-5 div span").parent();
        System.out.println("Размер: ");
        String rawText = size.text();
        System.out.println("Написано: " + rawText.substring(rawText.indexOf(',') + 2));
        System.out.println("Планируется: " + size.getElementsByTag("span").attr("title").replaceAll("<.{1,2}>", ""));
        // Метки
        Elements tags = fanficHat.select("div.tags a");
        System.out.println("Метки: ");
        tags.forEach(tag -> {
            try {
                System.out.println(new Tag(tag));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        // Описание
        Element description = fanficHat.selectFirst("div[itemprop=description]");
        System.out.println("Описание: ");
        System.out.println(description.text());
        // Посвящение и примечания
        Elements dedicationAndNotes = fanficHat.select("div.urlize[itemprop!=description]");
        // Первое посвящение, второе примечания
        System.out.println("Посвящение и примечания");
        dedicationAndNotes.forEach(item -> {
            System.out.println("<--->");
            System.out.println(item.text());
            System.out.println("<--->");
        });
        // Copyright
        Element copyright = fanficHat.select("div.mb-5 div").last();
        System.out.println("Копирайт: ");
        System.out.println(copyright.text());
        // Награды // TODO
        Element rawRewardList = fanficHat.selectFirst("fanfic-reward-list");
        JsonElement rewards = JsonParser.parseString(rawRewardList.attr(":initial-fic-rewards-list"));
        if (rewards != null && rewards.isJsonArray()) {
            rewards.getAsJsonArray().forEach(reward -> parseReward(reward.getAsJsonObject()));
        }
        // Главы
        Elements chapters = doc.select("ul.list-of-fanfic-parts li.part");
        chapters.forEach(chapter -> {
            System.out.println("<--->");
            System.out.println("Название: " + chapter.selectFirst("h3").text());
            System.out.println("Ссылка: " + chapter.selectFirst("a").attr("href"));
            Element metaData = chapter.selectFirst("div.part-info");
            System.out.println("Дата: " + metaData.selectFirst("span").attr("title"));
            Element reviews = metaData.selectFirst("a");
            if (reviews != null) {
                System.out.println(reviews.text() + " Ссылка: " + reviews.attr("href"));
            }
            System.out.println("<--->");
        });
    }

    public static void parseFanficView(Element article) {
//        Document doc = Jsoup.connect("https://ficbook.net/fanfiction/anime_and_manga/sword_art_online").get();
//        Elements articles = doc.select("article.block");
//        Element article = articles.first();
        // Название
        Element title = article.selectFirst("a.visit-link");
        System.out.printf("Название работы: %s Ссылка: %s%n", title.text(), title.attr("href"));
        // Жанр
        System.out.println("Жанр: " + article.selectFirst("span.direction").attr("title").replaceAll("\\s{2,}", " "));
        System.out.println(article.selectFirst("span.direction"));
        // Лайки
        System.out.println("Реакции: " + article.select("span.value").text());
        // Авторы
        System.out.println("Авторы: ");
        Element author = article.selectFirst("div.authors-list a");
        System.out.printf("Имя: %s Ссылка: %s%n", author.text(), author.attr("href"));
        // Информация
        Elements fanficHat = article.select("dl.info dd");
        // Фандом/ы
        System.out.println("Фандомы: ");
        Elements fandoms = fanficHat.select("a[class=js-open-notification-modal]");
        fandoms.forEach(fandom -> {
            System.out.printf("Ссылка: %s Название: %s%n", fandom.attr("href"), fandom.text());
        });
        // Пейринги/Персонажи
        Elements pairings = article.select("a.pairing-link");
        if (!pairings.isEmpty()) {
            System.out.println("Пейринги/Персонажи: ");
            pairings.forEach(pairing -> {
                System.out.printf("Ссылка: %s Название: %s%n", pairing.attr("href"), pairing.text());
//                System.out.println(StringEscapeUtils.escapeHtml4(pairing.attr("href")));
            });
        }
        // Рейтинг
        Elements rAndSize = fanficHat.select("strong[title]");
        Element rating = rAndSize.get(0);
        System.out.println("Рейтинг: ");
        System.out.printf("Название: %s Описание: %s%n", rating.text(), rating.attr("title").replaceAll("<.{1,2}>", ""));
        // Размер
        System.out.println("Размер: ");
        Element size = rAndSize.get(1);
        System.out.printf("Название: %s Описание: %s%n", size.text(), size.attr("title").replaceAll("<.{1,2}>", ""));
        String pattern = size.parent().text();
        System.out.println("Написано " + pattern.substring(pattern.indexOf("написано ") + 9));
        // Статус
        Element status = fanficHat.select("span[style]").first();
        System.out.println("Статус: " + status.text());
        // Метки
        System.out.println("Метки: ");
        Elements tags = fanficHat.select("a.tag");
        tags.forEach(tag -> {
            System.out.printf("Название: %s Ссылка: %s Описание: %s%n", tag.text(), tag.attr("href"), tag.attr("title").replaceAll("<.{1,2}>", ""));
        });
        // Описание
        System.out.println("Описание: ");
        System.out.println(article.select("div.fanfic-description").text());
    }
}
