package ru.otus.hw05.service;

import ru.otus.hw05.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> getAll();

    Optional<Book> getById(long id);

    void save(Book book);

    void delete(Book book);
}
