package ru.otus.hw06.repository;

import ru.otus.hw06.model.Genre;

import java.util.List;

public interface GenreRepository {
    List<Genre> getAll();
}
