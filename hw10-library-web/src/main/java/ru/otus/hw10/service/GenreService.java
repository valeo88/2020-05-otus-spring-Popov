package ru.otus.hw10.service;

import ru.otus.hw10.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {
    Optional<Genre> getById(long id);
    List<Genre> getAll();
}
