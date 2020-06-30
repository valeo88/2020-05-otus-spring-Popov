package ru.otus.hw05.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Book {
    private final long id;
    private final String name;
    private final Author author;
    private final Genre genre;
}
