package ru.otus.hw09.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw09.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
