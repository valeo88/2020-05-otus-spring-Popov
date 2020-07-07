package ru.otus.hw06.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.hw06.dao.BookDao;
import ru.otus.hw06.model.Book;
import ru.otus.hw06.service.BookSaveException;
import ru.otus.hw06.service.BookService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Transactional
    @Override
    public List<Book> getAll() {
        return bookDao.getAll();
    }

    @Transactional
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
}
