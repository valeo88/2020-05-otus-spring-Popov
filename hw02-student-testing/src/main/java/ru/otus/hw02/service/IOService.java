package ru.otus.hw02.service;

public interface IOService {
    void writeText(String text);
    void writeError(String text);
    String readText();
}
