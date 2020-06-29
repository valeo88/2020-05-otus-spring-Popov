package ru.otus.hw04.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.hw04.service.IOService;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

@Service
public class ConsoleIOService implements IOService {

    private PrintStream out;
    private PrintStream err;
    private Scanner scanner;

    public ConsoleIOService() {
        this.out = new PrintStream(System.out);
        this.err = new PrintStream(System.err);
        this.scanner = new Scanner(System.in);
    }

    public ConsoleIOService(OutputStream out, OutputStream err, InputStream in) {
        this.out = new PrintStream(out);
        this.err = new PrintStream(err);
        this.scanner = new Scanner(in);
    }

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
