package com.github.romanqed.api.html;

import com.github.romanqed.api.util.Checks;
import com.github.romanqed.api.util.Urls;
import com.github.romanqed.concurrent.BaseTaskFabric;
import com.github.romanqed.concurrent.Task;
import com.github.romanqed.concurrent.TaskFabric;
import okhttp3.OkHttpClient;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class PairingLoader extends AbstractHtmlLoader<Pairing> {
    public PairingLoader(OkHttpClient client, TaskFabric<Pairing> taskFabric) {
        this.client = Checks.requireNonNullElse(client, new OkHttpClient());
        this.taskFabric = Checks.requireNonNullElse(taskFabric, new BaseTaskFabric<>());
    }

    public PairingLoader(OkHttpClient client) {
        this(client, null);
    }

    public PairingLoader() {
        this(null);
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
    protected Pairing fromResponse(URL url, String body) {
        Pairing ret = new Pairing(url);
        ret.fromPage(body);
        return ret;
    }

    @Override
    protected URL makeUrl(String id) {
        return Urls.attachUrl(Urls.PAIRINGS, id);
    }
}
