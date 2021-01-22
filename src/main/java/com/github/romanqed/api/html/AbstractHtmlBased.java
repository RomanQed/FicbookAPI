package com.github.romanqed.api.html;

import com.github.romanqed.api.AbstractLinkable;
import com.github.romanqed.concurrent.AbstractTask;
import com.github.romanqed.concurrent.Task;
import com.github.romanqed.concurrent.TaskFabric;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.concurrent.Callable;

public abstract class AbstractHtmlBased extends AbstractLinkable {
    protected OkHttpClient client;

    @Override
    public Task<Void> load(TaskFabric<Void> taskFabric) {
        Callable<Void> taskBody = () -> {
            Request request = new Request.Builder()
                .url(getLink())
                .method("GET", null)
                .build();
            // TODO Добавить nullptr Checks.requireNonNull
            Document body = Jsoup.parse(client.newCall(request).execute().body().string());
            fromPage(body);
            return null;
        };
        if (taskFabric != null) {
            return taskFabric.createTask(taskBody);
        } else {
            return new AbstractTask<Void>() {
                @Override
                public Void now() throws Exception {
                    return taskBody.call();
                }
            };
        }
    }

    protected abstract void fromPage(Document document);
}
