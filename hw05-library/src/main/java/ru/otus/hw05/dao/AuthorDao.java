package ru.otus.hw05.dao;

import ru.otus.hw05.model.Author;

import java.util.List;

public interface AuthorDao {
    List<Author> getAll();
}
