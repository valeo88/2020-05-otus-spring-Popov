package ru.otus.hw11.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw11.model.Genre;

public interface GenreService {
    Mono<Genre> getById(String id);
    Flux<Genre> getAll();
}
