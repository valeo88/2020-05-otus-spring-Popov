package ru.otus.hw11.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw11.dto.BookDto;
import ru.otus.hw11.dto.CommentDto;
import ru.otus.hw11.model.Book;

public interface BookService {
    Flux<BookDto> getAll();

    Mono<BookDto> find(String id);

    Mono<BookDto> save(Book book);

    Mono<Void> delete(Book book);

    Flux<CommentDto> getComments(Book book);

    Mono<CommentDto> addComment(Book book, String text);
}
