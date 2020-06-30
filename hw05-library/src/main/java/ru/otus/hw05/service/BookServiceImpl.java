package ru.otus.hw05.service;

import org.springframework.stereotype.Service;
import ru.otus.hw05.dao.BookDao;
import ru.otus.hw05.model.Book;

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
    public void save(Book book) {
        find(book.getId()).ifPresentOrElse(bookDao::update, () -> bookDao.insert(book));
    }

    @Override
    public void delete(Book book) {
        bookDao.deleteById(book.getId());
    }
}
