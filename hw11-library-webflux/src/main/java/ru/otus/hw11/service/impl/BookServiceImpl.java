package ru.otus.hw11.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw11.dto.BookDto;
import ru.otus.hw11.dto.CommentDto;
import ru.otus.hw11.model.Book;
import ru.otus.hw11.model.Comment;
import ru.otus.hw11.repository.BookRepository;
import ru.otus.hw11.repository.CommentRepository;
import ru.otus.hw11.service.BookSaveException;
import ru.otus.hw11.service.BookService;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;

    public BookServiceImpl(BookRepository bookRepository, CommentRepository commentRepository) {
        this.bookRepository = bookRepository;
        this.commentRepository = commentRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public Flux<BookDto> getAll() {
        return bookRepository.findAll()
                .map(BookDto::fromBook);
    }

    @Transactional(readOnly = true)
    @Override
    public Mono<BookDto> find(String id) {
        return bookRepository.findById(id).map(BookDto::fromBook);
    }

    @Transactional
    @Override
    public Mono<BookDto> save(Book book) {
        try {
            return bookRepository.save(book).map(BookDto::fromBook);
        } catch (Exception e) {
            throw new BookSaveException(String.format("Error on save book: %s, %s", book, e.getMessage()));
        }
    }

    @Transactional
    @Override
    public Mono<Void> delete(Book book) {
        commentRepository.deleteAll(commentRepository.findByBook(book));
        return bookRepository.deleteById(book.getId());
    }

    @Transactional(readOnly = true)
    @Override
    public Flux<CommentDto> getComments(Book book) {
        return commentRepository.findByBook(book)
                .map(CommentDto::fromComment);
    }

    @Transactional
    @Override
    public Mono<CommentDto> addComment(Book book, String text) {
        Comment comment = new Comment();
        comment.setValue(text);
        comment.setBook(book);
        return commentRepository.save(comment).map(CommentDto::fromComment);
    }
}
