package ru.otus.hw08.service;

import ru.otus.hw08.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    Optional<Author> getById(String id);
    List<Author> getAll();
}
