package ru.otus.hw08.service;

import ru.otus.hw08.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {
    Optional<Genre> getById(String id);
    List<Genre> getAll();
}
