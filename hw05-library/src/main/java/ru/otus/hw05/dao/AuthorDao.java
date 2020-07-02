package ru.otus.hw05.dao;

import ru.otus.hw05.model.Author;

import java.util.List;

public interface AuthorDao {
    void insert(Author author);

    Author getByFullName(String fullName);

    List<Author> getAll();
}
