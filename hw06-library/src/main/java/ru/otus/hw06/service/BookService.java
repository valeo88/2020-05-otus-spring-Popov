package ru.otus.hw06.service;

import ru.otus.hw06.model.Book;
import ru.otus.hw06.model.Comment;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> getAll();

    Optional<Book> find(long id);

    Book save(Book book);

    void delete(Book book);

    List<Comment> getComments(Book book);

    Comment addComment(Book book, String text);
}
