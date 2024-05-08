package org.alx.article._24_solid.liskov_substitution_principle.logger_example;

import java.io.FileWriter;
import java.io.IOException;

class Logger {
    void log(String text) {
        System.out.println(text);
    }
}

class FileLogger extends Logger {
    private final String path;

    FileLogger(String path) {
        this.path = path;
    }

    @Override
    void log(String text) {
        try {
            FileWriter writer = new FileWriter(path, true);
            writer.write(text + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class HttpLogger extends Logger {
    private final String ip;
    private final int port;

    HttpLogger(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    void log(String text) {
        // implementation
    }

    void openConnection() {
        // implementation
    }

    void closeConnection() {
        // implementation
    }
}

public class LoggerExample {
}
