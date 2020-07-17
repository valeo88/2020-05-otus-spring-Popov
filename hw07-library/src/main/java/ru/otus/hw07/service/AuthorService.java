package ru.otus.hw07.service;

import ru.otus.hw07.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    Optional<Author> getById(long id);
    List<Author> getAll();
}
