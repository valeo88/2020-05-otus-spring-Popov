package ru.otus.hw09.service;

import ru.otus.hw09.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {
    Optional<Genre> getById(long id);
    List<Genre> getAll();
}
