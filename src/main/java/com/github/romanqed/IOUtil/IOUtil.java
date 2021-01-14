package com.github.romanqed.IOUtil;


import java.io.*;
import java.nio.charset.StandardCharsets;

public class IOUtil {
    public static String readFile(Reader reader) throws IOException {
        StringBuilder out = new StringBuilder();
        char[] buf = new char[1024];
        for (int i = reader.read(buf); i >= 0; i = reader.read(buf)) {
            out.append(buf, 0, i);
        }
        return out.toString();
    }

    public static String readFile(File file) throws IOException {
        InputStream inputStream = new FileInputStream(file);
        return readFile(new InputStreamReader(inputStream));
    }

    public static void writeFile(Writer writer, String body) throws IOException {
        writer.append(body);
        writer.flush();
        writer.close();
    }

    public static void writeFile(File file, String body) throws IOException {
        OutputStream outputStream = new FileOutputStream(file);
        Writer writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
        writeFile(writer, body);
    }
}
