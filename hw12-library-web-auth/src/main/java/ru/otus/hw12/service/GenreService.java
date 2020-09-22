package ru.otus.hw12.service;

import ru.otus.hw12.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {
    Optional<Genre> getById(long id);
    List<Genre> getAll();
}
