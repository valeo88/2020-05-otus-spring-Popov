package ru.otus.hw05.dao;

import ru.otus.hw05.model.Genre;

import java.util.List;

public interface GenreDao {
    void insert(Genre genre);

    Genre getByName(String name);

    List<Genre> getAll();
}
