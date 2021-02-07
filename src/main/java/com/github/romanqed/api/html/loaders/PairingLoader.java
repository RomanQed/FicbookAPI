package com.github.romanqed.api.html.loaders;

import com.github.romanqed.api.html.AbstractHtmlBased;
import com.github.romanqed.api.html.AbstractHtmlLoader;
import com.github.romanqed.api.html.entites.Pairing;
import com.github.romanqed.api.util.Urls;
import com.github.romanqed.concurrent.Task;
import com.github.romanqed.concurrent.TaskFabric;
import kong.unirest.UnirestInstance;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class PairingLoader extends AbstractHtmlLoader<Pairing> {
    public PairingLoader(UnirestInstance client, TaskFabric taskFabric) {
        super(client, taskFabric);
    }

    public PairingLoader(UnirestInstance client) {
        this(client, null);
    }

    public PairingLoader() {
        this(null, null);
    }

    public Task<Pairing> load(List<String> characters) {
        StringBuilder rawLink = new StringBuilder();
        characters.forEach(character -> rawLink.append('/').append(Urls.encodeFicbookUrl(character)));
        return super.load(rawLink.toString());
    }

    public Task<Pairing> load(String... characters) {
        return load(Arrays.asList(characters));
    }

    @Override
    protected AbstractHtmlBased.AbstractHtmlBuilder<Pairing> getBuilder() {
        return Pairing.BUILDER;
    }

    @Override
    protected URL makeUrl(String id) {
        return Urls.attachUrl(Urls.PAIRINGS, id);
    }
}
