package ru.otus.hw06.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw06.dao.BookDao;
import ru.otus.hw06.model.Book;
import ru.otus.hw06.model.Comment;
import ru.otus.hw06.service.BookNotFoundException;
import ru.otus.hw06.service.BookSaveException;
import ru.otus.hw06.service.BookService;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> getAll() {
        return bookDao.getAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Book> find(long id) {
        return bookDao.getById(id);
    }

    @Transactional
    @Override
    public Book save(Book book) {
        long id = book.getId();
        try {
            if (book.getId() == 0) {
                id = bookDao.insert(book);
            } else {
                bookDao.update(book);
            }
        } catch (Exception e) {
            throw new BookSaveException("Something wrong in saving book: " + e.getMessage());
        }
        return bookDao.getById(id).get();
    }

    @Transactional
    @Override
    public void delete(Book book) {
        bookDao.deleteById(book.getId());
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> getComments(Book book) {
        Book reloaded = find(book.getId()).orElseThrow(BookNotFoundException::new);
        return reloaded.getComments();
    }

    @Transactional
    @Override
    public Comment addComment(Book book, String text) {
        Book reloaded = bookDao.getById(book.getId())
                .orElseThrow(BookNotFoundException::new);

        Comment comment = new Comment();
        comment.setValue(text);
        comment.setBook(reloaded);

        reloaded.getComments().add(comment);
        bookDao.update(reloaded);

        return comment;
    }
}
