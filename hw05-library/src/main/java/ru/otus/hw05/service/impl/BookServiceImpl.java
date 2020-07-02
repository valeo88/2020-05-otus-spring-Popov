package ru.otus.hw05.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.hw05.dao.BookDao;
import ru.otus.hw05.model.Book;
import ru.otus.hw05.service.BookSaveException;
import ru.otus.hw05.service.BookService;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public List<Book> getAll() {
        return bookDao.getAll();
    }

    @Override
    public Optional<Book> find(long id) {
        return Optional.ofNullable(bookDao.getById(id));
    }

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
        return bookDao.getById(id);
    }

    @Override
    public void delete(Book book) {
        bookDao.deleteById(book.getId());
    }
}
