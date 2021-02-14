package com.github.romanqed.api.interfaces.lambdas;

import java.net.URL;

@FunctionalInterface
public interface AnswerProcessor<RET> {
    RET handle(URL url, String body) throws Exception;
}
