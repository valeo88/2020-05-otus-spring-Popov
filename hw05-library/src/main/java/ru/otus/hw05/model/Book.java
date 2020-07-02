package ru.otus.hw05.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Book {
    private long id;
    private final String name;
    private final Author author;
    private final Genre genre;
}
