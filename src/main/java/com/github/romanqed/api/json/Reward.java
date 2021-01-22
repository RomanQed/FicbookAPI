package com.github.romanqed.api.json;

import com.github.romanqed.api.util.Checks;
import com.github.romanqed.api.util.Patterns;
import com.google.gson.JsonObject;

import java.util.Date;


// TODO Add User class
public class Reward {
    private final int userId;
    private final String userName;
    private final Date createDate;
    private final String rewardText;

    public Reward(JsonObject jsonObject) throws Exception {
        userId = Checks.requireNonExcept(() -> jsonObject.get("giver_id").getAsInt(), -1);
        userName = Checks.requireNonExcept(() -> jsonObject.get("nickname").getAsString(), "");
        createDate = Patterns.dateFormat.parse(jsonObject.get("added").getAsString());
        rewardText = jsonObject.get("user_text").getAsString();
    }

    public boolean brokenUser() {
        return userId == -1 || userName.isEmpty();
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

    public String getRewardText() {
        return rewardText;
    }

    @Override
    public String toString() {
        return "[Reward] " + rewardText + " [User] " + userId + " [Date] " + createDate;
    }
}
