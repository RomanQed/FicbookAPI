package com.github.romanqed.api.json;

import com.github.romanqed.api.interfaces.HtmlBuilder;
import com.github.romanqed.api.util.Checks;
import com.github.romanqed.api.util.ParseUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Comment {
    public static final HtmlBuilder<Comment> BUILDER = new CommentBuilder();
    private final int userId;
    private String userName;
    private Date createDate;
    private String comment;
    private final int likes;
    private final List<Reward> rewards;

    public Comment(JsonObject jsonObject) {
        rewards = new ArrayList<>();
        userId = Checks.requireNonExcept(() -> jsonObject.get("user_id").getAsInt(), -1);
        userName = Checks.requireNonExcept(() -> jsonObject.get("user_nickname").getAsString(), "");
        createDate = ParseUtil.parseJsonDate(jsonObject.get("date_create").getAsString());
        comment = jsonObject.get("comment").getAsString();
        likes = Checks.requireNonExcept(() -> jsonObject.get("like_cnt").getAsInt(), 0);
        JsonArray rawRewards = Checks.requireNonExcept(() -> jsonObject.get("rewards").getAsJsonArray(), null);
        if (rawRewards != null) {
            for (JsonElement rawReward : rawRewards) {
                rewards.add(new Reward(rawReward.getAsJsonObject()));
            }
        }
    }

    public Comment() {
        userId = -1;
        likes = 0;
        rewards = new ArrayList<>();
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public String getComment() {
        return comment;
    }

    public int getLikes() {
        return likes;
    }

    public List<Reward> getRewards() {
        return rewards;
    }

    @Override
    public String toString() {
        return "[Comment] " + comment + " [User] " + userId + " [Date] "
                + createDate + " [Likes] " + likes + " [Rewards] " + rewards;
    }

    public static class CommentBuilder implements HtmlBuilder<Comment> {
        @Override
        public Comment build(Element node) {
            Element comment = node.selectFirst("comment-like");
            if (comment != null) {
                return new Comment(JsonParser.parseString(comment.attr(":comment")).getAsJsonObject());
            }
            Comment ret = new Comment();
            Element header = node.selectFirst("header.head");
            ret.userName = header.selectFirst("span.comment_author").text();
            ret.createDate = ParseUtil.parseNativeDate(header.selectFirst("time").attr("datetime"));
            ret.comment = node.selectFirst("div.comment_message").text();
            return ret;
        }
    }
}
