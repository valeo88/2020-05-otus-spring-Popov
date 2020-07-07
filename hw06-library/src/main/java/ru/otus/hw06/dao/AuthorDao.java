package ru.otus.hw06.dao;

import ru.otus.hw06.model.Author;

import java.util.List;

public interface AuthorDao {
    List<Author> getAll();
}
