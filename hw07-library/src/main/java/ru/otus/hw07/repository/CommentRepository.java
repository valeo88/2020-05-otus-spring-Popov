package ru.otus.hw07.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw07.model.Book;
import ru.otus.hw07.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBook(Book book);
}
