package ru.otus.hw08.repository;

import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.hw08.model.Author;
import ru.otus.hw08.model.Book;
import ru.otus.hw08.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Spring Data Mongo для работы с книгами ")
@DataMongoTest
public class BookRepositoryTest {

    private static final List<Book> books = List.of(
            new Book("1", "Book1", new Author("1", "1"), new Genre("2", "2")),
            new Book("2", "Book2", new Author("2", "2"), new Genre("2", "2")),
            new Book("3", "Book3", new Author("1", "1"), new Genre("1", "1"))
    );

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    private void setUp() {
        books.forEach(mongoTemplate::save);
    }

    @AfterEach
    private void clear() {
        mongoTemplate.dropCollection(Book.class);
    }

    @DisplayName("должен выдавать корректное количество книг")
    @Test
    void shouldReturnCorrectCountOfAllBooks() {
        val count = bookRepository.count();
        assertThat(count).isEqualTo(books.size());
    }

    @DisplayName("должен загружать список всех книг с полной информацией о них")
    @Test
    void shouldReturnCorrectGenresListWithAllInfo() {
        val booksLoaded = bookRepository.findAll();
        assertThat(booksLoaded).isNotNull().hasSize(books.size())
                .allMatch(b -> !b.getName().equals(""))
                .allMatch(b -> b.getAuthor()!=null)
                .allMatch(b -> b.getGenre()!=null)
                .allMatch(b -> b.getId()!=null);
    }

    @DisplayName("должен выдавать корректный идентификатор новой книги при вставке")
    @Test
    void shouldReturnCorrectBookIdWhenInsertedNew() {
        val book = new Book("0", "Test", new Author("1",null), new Genre("1",null));

        val saved = bookRepository.save(book);
        assertThat(saved).isNotNull().matches(b -> b.getId()!=null);
    }

    @DisplayName("должен изменить книгу при обновлении")
    @Test
    void shouldCorrectUpdateBook() {
        val book = new Book("1","Test", new Author("1",null), new Genre("1",null));

        val updated = bookRepository.save(book);

        assertThat(updated).isNotNull()
                .matches(b -> b.getId().equals("1"))
                .matches(b -> b.getAuthor().getId().equals("1"))
                .matches(b -> b.getGenre().getId().equals("1"));
    }

    @DisplayName("должен выдавать книгу по существующему идентификатору")
    @Test
    void shouldReturnBookWhenIdExists() {
        val book = bookRepository.findById("1");

        assertThat(book).isNotEmpty().get()
                .matches(b -> b.getId().equals("1"))
                .matches(b -> b.getAuthor().getId().equals("1"))
                .matches(b -> b.getGenre().getId().equals("2"));
    }

    @DisplayName("должен выдавать Empty по несуществующему идентификатору")
    @Test
    void shouldReturnEmptyWhenIdNotExists() {
        val book = bookRepository.findById("");

        assertThat(book).isEmpty();
    }

    @DisplayName("должен удалять книгу по существующему идентификатору")
    @Test
    void shouldDeleteBookWhenIdExists() {
        val id = "1";

        bookRepository.deleteById(id);

        assertThat(mongoTemplate.findById(id, Book.class)).isNull();
    }
}
