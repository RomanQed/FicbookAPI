package com.github.romanqed.api.interfaces;

import com.google.gson.JsonObject;

public interface JsonBased {
    void fromJson(JsonObject jsonObject) throws Exception;
}
