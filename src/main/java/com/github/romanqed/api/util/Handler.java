package com.github.romanqed.api.util;

@FunctionalInterface
public interface Handler<RET, INPUT> {
    RET handle(INPUT input) throws Exception;
}
