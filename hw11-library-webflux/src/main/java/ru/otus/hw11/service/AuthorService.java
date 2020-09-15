package ru.otus.hw11.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw11.model.Author;

public interface AuthorService {
    Mono<Author> getById(String id);
    Flux<Author> getAll();
}
