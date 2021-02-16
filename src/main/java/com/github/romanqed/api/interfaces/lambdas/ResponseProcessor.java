package com.github.romanqed.api.interfaces.lambdas;

import java.net.URL;

@FunctionalInterface
public interface ResponseProcessor<RET> {
    RET handle(URL url, String body) throws Exception;
}
