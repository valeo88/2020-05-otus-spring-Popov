package ru.otus.hw04;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Locale;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        Locale.setDefault(Locale.forLanguageTag("ru-RU"));

        SpringApplication.run(Main.class, args);
    }
}
