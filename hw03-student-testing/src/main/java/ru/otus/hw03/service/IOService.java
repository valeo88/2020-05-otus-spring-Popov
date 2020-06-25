package ru.otus.hw03.service;

public interface IOService {
    void writeText(String text);
    void writeError(String text);
    String readText();
}
