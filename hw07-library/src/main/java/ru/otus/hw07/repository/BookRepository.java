package ru.otus.hw07.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw07.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
