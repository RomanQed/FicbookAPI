package com.github.romanqed.api;

import com.github.romanqed.api.util.Checks;
import com.github.romanqed.concurrent.AbstractTask;
import com.github.romanqed.concurrent.Task;
import com.github.romanqed.concurrent.TaskFabric;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.concurrent.Callable;

public abstract class AbstractHtmlBased extends AbstractLinkable {
    // FIXME
    public OkHttpClient client;

    @Override
    @SuppressWarnings("ConstantConditions")
    public Task<Response> load(TaskFabric<Response> taskFabric) {
        Callable<Response> taskBody = () -> {
            Request request = new Request.Builder()
                    .url(getLink())
                    .method("GET", null)
                    .build();
            Response response = client.newCall(request).execute();
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

    protected abstract void fromPage(String rawPage);
}
