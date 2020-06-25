package ru.otus.hw03.exception;

import java.io.IOException;

public class QuestionsLoadException extends RuntimeException {
    public QuestionsLoadException(String s, IOException e) {
        super(s);
    }
}
