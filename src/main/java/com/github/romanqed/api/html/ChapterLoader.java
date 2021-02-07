package com.github.romanqed.api.html;

import com.github.romanqed.api.util.Urls;
import com.github.romanqed.concurrent.Task;
import com.github.romanqed.concurrent.TaskFabric;
import kong.unirest.UnirestInstance;

import java.net.URL;

public class ChapterLoader extends AbstractHtmlLoader<Chapter> {
    public ChapterLoader(UnirestInstance client, TaskFabric taskFabric) {
        super(client, taskFabric);
    }

    public ChapterLoader(UnirestInstance client) {
        this(client, null);
    }

    public ChapterLoader() {
        this(null, null);
    }

    public Task<Chapter> load(int fanficId, int chapterId) {
        return super.load(fanficId + "/" + chapterId);
    }

    public Task<Chapter> load(int fanficId) {
        return super.load(Integer.toString(fanficId));
    }

    @Override
    protected URL makeUrl(String id) {
        return Urls.attachUrl(Urls.FANFIC, id);
    }

    @Override
    protected Class<Chapter> getElementClass() {
        return Chapter.class;
    }
}
