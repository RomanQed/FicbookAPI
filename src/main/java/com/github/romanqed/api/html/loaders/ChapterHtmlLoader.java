package com.github.romanqed.api.html.loaders;

import com.github.romanqed.api.html.AbstractLinkableHtmlLoader;
import com.github.romanqed.api.html.entites.Chapter;
import com.github.romanqed.api.interfaces.HtmlBuilder;
import com.github.romanqed.api.util.Urls;
import com.github.romanqed.concurrent.Task;
import com.github.romanqed.concurrent.TaskFabric;
import kong.unirest.UnirestInstance;

import java.net.URL;

public class ChapterHtmlLoader extends AbstractLinkableHtmlLoader<Chapter> {
    public ChapterHtmlLoader(UnirestInstance client, TaskFabric taskFabric) {
        super(client, taskFabric);
    }

    public ChapterHtmlLoader(UnirestInstance client) {
        this(client, null);
    }

    public ChapterHtmlLoader() {
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
    protected HtmlBuilder<Chapter> getBuilder() {
        return Chapter.BUILDER;
    }
}
