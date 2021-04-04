package com.github.romanqed.api.exchangers;

import com.github.romanqed.concurrent.TaskFabric;
import kong.unirest.UnirestInstance;

public class HtmlExchanger extends AsyncExchanger {
    public HtmlExchanger(UnirestInstance client, TaskFabric fabric) {
        super(client, fabric);
    }

    public HtmlExchanger(UnirestInstance client) {
        this(client, null);
    }

    public HtmlExchanger() {
        this(null, null);
    }
}
