package com.github.romanqed.api;

import com.github.romanqed.api.util.Checks;
import com.github.romanqed.concurrent.AbstractTask;
import com.github.romanqed.concurrent.Task;
import com.github.romanqed.concurrent.TaskFabric;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.concurrent.Callable;

public abstract class LinkableHtmlBased extends AbstractLinkable {
    @Override
    @SuppressWarnings("ConstantConditions")
    public Task<Response> load(TaskFabric<Response> taskFabric) {
        Callable<Response> taskBody = () -> {
            Response response = client.newCall(requestBuilder().build()).execute();
            if (response.code() / 100 != 2) {
                throw new IOException("Server returned HTTP response code: " + response.code());
            }
            fromPage(Checks.requireNonExcept(() -> response.body().string(), ""));
            return response;
        };
        if (taskFabric != null) {
            return taskFabric.createTask(taskBody);
        } else {
            return new AbstractTask<Response>() {
                @Override
                public Response call() throws Exception {
                    return taskBody.call();
                }
            };
        }
    }

    protected Request.Builder requestBuilder() {
        return new Request.Builder()
                .url(getLink())
                .method("GET", null);
    }

    protected abstract void fromPage(String rawPage);
}
