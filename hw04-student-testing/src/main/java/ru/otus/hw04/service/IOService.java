package ru.otus.hw04.service;

public interface IOService {
    void writeText(String text);
    void writeError(String text);
    String readText();
}
