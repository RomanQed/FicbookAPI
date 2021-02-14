package com.github.romanqed.api.interfaces;

import com.github.romanqed.api.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public interface Loader {
    String load(URL url, List<Pair<String, String>> fields) throws IOException;
}
