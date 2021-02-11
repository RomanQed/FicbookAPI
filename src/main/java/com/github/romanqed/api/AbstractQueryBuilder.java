package com.github.romanqed.api;

import com.github.romanqed.api.interfaces.QueryBuilder;
import com.github.romanqed.api.util.Pair;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractQueryBuilder implements QueryBuilder {
    protected List<Pair<String, String>> queries;

    @Override
    public List<Pair<String, String>> build() {
        List<Pair<String, String>> ret = new ArrayList<>(queries);
        queries.clear();
        return ret;
    }
}
