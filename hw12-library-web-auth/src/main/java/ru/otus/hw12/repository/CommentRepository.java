package ru.otus.hw12.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw12.model.Book;
import ru.otus.hw12.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBook(Book book);
}
