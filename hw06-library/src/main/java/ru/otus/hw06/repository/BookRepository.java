package ru.otus.hw06.repository;

import ru.otus.hw06.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    int count();

    Book save(Book book);

    Optional<Book> getById(long id);

    List<Book> getAll();

    void deleteById(long id);
}
