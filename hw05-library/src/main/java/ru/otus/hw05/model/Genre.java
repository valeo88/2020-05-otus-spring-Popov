package ru.otus.hw05.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Genre {
    private final long id;
    private final String name;
}
