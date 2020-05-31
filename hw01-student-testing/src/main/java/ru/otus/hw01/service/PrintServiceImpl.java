package ru.otus.hw01.service;

import java.io.OutputStream;
import java.io.PrintStream;

public class PrintServiceImpl implements PrintService {

    private final PrintStream printStream;
    private final PrintStream errorStream;

    public PrintServiceImpl(OutputStream outputStream, OutputStream errorStream) {
        this.printStream = new PrintStream(outputStream);
        this.errorStream = new PrintStream(errorStream);
    }

    @Override
    public void println(String text) {
        printStream.println(text);
    }

    @Override
    public void printError(String text) {
        errorStream.println(text);
    }
}
