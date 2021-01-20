package com.github.romanqed.api.interfaces;

import java.util.function.Consumer;

public interface MutableHtmlBased extends HtmlBased {
    default void buildBy(Consumer<MutableHtmlBased> builder) {
        try {
            builder.accept(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
