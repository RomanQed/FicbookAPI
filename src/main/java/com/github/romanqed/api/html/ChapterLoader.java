package com.github.romanqed.api.html;

import com.github.romanqed.api.AbstractDataLoader;
import com.github.romanqed.api.util.Urls;
import com.github.romanqed.concurrent.Task;
import com.github.romanqed.concurrent.TaskFabric;
import okhttp3.OkHttpClient;

import java.net.URL;

public class ChapterLoader extends AbstractDataLoader<Chapter> {
    public ChapterLoader(OkHttpClient client, TaskFabric taskFabric) {
        super(client, taskFabric);
    }

    public ChapterLoader(OkHttpClient client) {
        this(client, null);
    }

    public ChapterLoader() {
        this(null, null);
    }

    public Task<Chapter> load(int fanficId, int chapterId) {
        return super.load(fanficId + "/" + chapterId);
    }

    @Override
    protected Chapter fromResponse(URL url, String body) {
        Chapter ret = new Chapter(url);
        ret.fromPage(body);
        return ret;
    }

    @Override
    protected URL makeUrl(String id) {
        return Urls.attachUrl(Urls.FANFIC, id);
    }
}
