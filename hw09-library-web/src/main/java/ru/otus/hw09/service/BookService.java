package ru.otus.hw09.service;

import ru.otus.hw09.dto.BookDto;
import ru.otus.hw09.dto.CommentDto;
import ru.otus.hw09.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<BookDto> getAll();

    Optional<BookDto> find(long id);

    BookDto save(Book book);

    void delete(Book book);

    List<CommentDto> getComments(Book book);

    CommentDto addComment(Book book, String text);
}
