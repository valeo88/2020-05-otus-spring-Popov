package ru.otus.hw10.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw10.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
