package ru.otus.hw06.service;

import ru.otus.hw06.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> getAll();

    Optional<Book> find(long id);

    Book save(Book book);

    void delete(Book book);
}
