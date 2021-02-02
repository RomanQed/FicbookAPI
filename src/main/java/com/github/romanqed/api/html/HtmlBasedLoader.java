package com.github.romanqed.api.html;

import com.github.romanqed.concurrent.Task;
import com.github.romanqed.concurrent.TaskFabric;

public interface HtmlBasedLoader <T extends AbstractHtmlBased> {
    Task<T> load(String id);

    default Task<T> load() {
        return load("");
    }

    TaskFabric<T> getTaskFabric();
}
