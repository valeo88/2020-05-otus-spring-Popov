package ru.otus.hw06.repository;

import ru.otus.hw06.model.Author;

import java.util.List;

public interface AuthorRepository {
    List<Author> getAll();
}
