package com.github.romanqed.api.html;

import com.github.romanqed.api.util.Checks;
import com.github.romanqed.api.util.Urls;
import com.github.romanqed.concurrent.Task;
import com.github.romanqed.concurrent.TaskFabric;
import okhttp3.OkHttpClient;

import java.net.URL;

public class UserLoader extends AbstractLinkableLoader<User> {
    public UserLoader(OkHttpClient client, TaskFabric taskFabric) {
        super(client, taskFabric);
    }

    public UserLoader(OkHttpClient client) {
        this(client, null);
    }

    public UserLoader() {
        this(null, null);
    }

    public Task<User> load(int id) {
        return load(Integer.toString(id));
    }

    @Override
    protected User fromResponse(URL url, String body) {
        User ret = new User(url);
        ret.fromPage(body);
        return ret;
    }

    @Override
    protected URL makeUrl(String id) {
        int parsedId = Integer.parseInt(id);
        return Urls.attachUrl(Urls.AUTHORS, Integer.toString(Checks.requireCorrectValue(parsedId, rawId -> rawId > 0)));
    }
}
