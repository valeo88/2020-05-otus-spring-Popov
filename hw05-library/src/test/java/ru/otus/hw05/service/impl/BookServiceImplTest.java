package ru.otus.hw05.service.impl;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hw05.dao.BookDao;
import ru.otus.hw05.model.Author;
import ru.otus.hw05.model.Book;
import ru.otus.hw05.model.Genre;
import ru.otus.hw05.service.BookService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@DisplayName("BookServiceImpl должен")
class BookServiceImplTest {

    private BookDao bookDao = mock(BookDao.class);
    private BookService bookService;

    @BeforeEach
    void setUp() throws IOException {
        when(bookDao.getAll()).thenReturn(
                sampleBooks()
        );
        when(bookDao.insert(any())).thenReturn(
                1L
        );
        when(bookDao.getById(1L)).thenReturn(
                Optional.of(sampleBooks().get(0))
        );

        bookService = new BookServiceImpl(bookDao);
    }

    @DisplayName(" должен возвращать корректный список книг")
    @Test
    void shouldReturnQuestionsList() {
        val books = bookService.getAll();

        assertThat(books).isEqualTo(sampleBooks());
    }

    @DisplayName(" должен вызывать нужные методы dao при сохранении книги")
    @Test
    void shouldCallCorrectBookDaoMethodsOnSave() {
        val newBook = new Book("Test 3", new Author(1, "1"),
                new Genre(1, "1"));
        bookService.save(newBook);
        verify(bookDao, times(1)).insert(newBook);

        val existedBook = sampleBooks().get(0);
        bookService.save(existedBook);
        verify(bookDao, times(1)).update(existedBook);
    }


    private List<Book> sampleBooks() {
        return Arrays.asList(new Book(1, "test 1", new Author(1, "1"),
                new Genre(1, "1")), new Book(2, "test 2", new Author(1, "1"),
                new Genre(2, "2")));
    }
}