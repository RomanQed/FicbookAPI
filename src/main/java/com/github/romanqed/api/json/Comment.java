package com.github.romanqed.api.json;

import com.github.romanqed.api.util.Checks;
import com.github.romanqed.api.util.Patterns;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// TODO Add User class
public class Comment {
    private final int userId;
    private final String userName;
    private final Date createDate;
    private final String comment;
    private final int likes;
    private final List<Reward> rewards;

    public Comment(JsonObject jsonObject) throws Exception {
        rewards = new ArrayList<>();
        userId = Checks.requireNonExcept(() -> jsonObject.get("user_id").getAsInt(), -1);
        userName = Checks.requireNonExcept(() -> jsonObject.get("user_nickname").getAsString(), "");
        createDate = Patterns.dateFormat.parse(jsonObject.get("date_create").getAsString());
        comment = jsonObject.get("comment").getAsString();
        likes = Checks.requireNonExcept(() -> jsonObject.get("like_cnt").getAsInt(), 0);
        JsonArray rawRewards = Checks.requireNonExcept(() -> jsonObject.get("rewards").getAsJsonArray(), null);
        if (rawRewards != null) {
            for (JsonElement rawReward: rawRewards) {
                rewards.add(new Reward(rawReward.getAsJsonObject()));
            }
        }
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
        return "[Comment] " + comment + " [User] " + userId + " [Date] " + createDate + " [Likes] " + likes + " [Rewards] " + rewards;
    }
}
