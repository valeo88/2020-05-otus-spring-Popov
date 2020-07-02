package ru.otus.hw05.service;

import org.springframework.stereotype.Service;
import ru.otus.hw05.dao.AuthorDao;
import ru.otus.hw05.dao.BookDao;
import ru.otus.hw05.dao.GenreDao;
import ru.otus.hw05.model.Book;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    public BookServiceImpl(BookDao bookDao, AuthorDao authorDao, GenreDao genreDao) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
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
        // todo load genre and author
        if (book.getId() == 0) {
            bookDao.insert(book);
        } else {
            bookDao.update(book);
        }
    }

    @Override
    public void delete(Book book) {
        bookDao.deleteById(book.getId());
    }
}
