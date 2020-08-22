package ru.otus.hw09.service;

import ru.otus.hw09.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    Optional<Author> getById(long id);
    List<Author> getAll();
}
