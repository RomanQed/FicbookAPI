package com.github.romanqed.api.interfaces;

import com.github.romanqed.api.util.Pair;

import java.util.List;

public interface QueryBuilder {
    List<Pair<String, String>> build();
}
