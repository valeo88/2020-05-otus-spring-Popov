package ru.otus.hw02.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.hw02.service.IOService;

import java.io.PrintStream;
import java.util.Scanner;

@Service
public class ConsoleIOService implements IOService {

    private final PrintStream out = new PrintStream(System.out);
    private final PrintStream err = new PrintStream(System.err);
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void writeText(String text) {
        out.println(text);
    }

    @Override
    public void writeError(String text) {
        err.println(text);
    }

    @Override
    public String readText() {
        return scanner.nextLine();
    }
}
