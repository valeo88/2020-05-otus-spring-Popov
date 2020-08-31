package ru.otus.hw10.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw10.model.Book;
import ru.otus.hw10.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBook(Book book);
}
