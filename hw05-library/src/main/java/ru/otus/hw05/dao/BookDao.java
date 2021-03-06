package ru.otus.hw05.dao;

import ru.otus.hw05.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    int count();

    long insert(Book book);

    void update(Book book);

    Optional<Book> getById(long id);

    List<Book> getAll();

    void deleteById(long id);
}
