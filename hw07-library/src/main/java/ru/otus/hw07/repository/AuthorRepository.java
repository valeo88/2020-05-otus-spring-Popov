package ru.otus.hw07.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw07.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
