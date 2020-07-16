package ru.otus.hw07.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.hw07.model.Author;
import ru.otus.hw07.model.Book;
import ru.otus.hw07.model.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Spring Data для работы с книгами ")
@DataJpaTest
public class BookRepositoryTest {

    private static final int EXPECTED_NUMBER_OF_BOOKS = 4;

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private TestEntityManager entityManager;

    @DisplayName("должен выдавать корректное количество книг")
    @Test
    void shouldReturnCorrectCountOfAllBooks() {
        val count = bookRepository.count();
        assertThat(count).isEqualTo(EXPECTED_NUMBER_OF_BOOKS);
    }

    @DisplayName("должен загружать список всех книг с полной информацией о них")
    @Test
    void shouldReturnCorrectGenresListWithAllInfo() {
        val books = bookRepository.findAll();
        assertThat(books).isNotNull().hasSize(EXPECTED_NUMBER_OF_BOOKS)
                .allMatch(b -> !b.getName().equals(""))
                .allMatch(b -> b.getAuthor()!=null)
                .allMatch(b -> b.getGenre()!=null)
                .allMatch(b -> b.getId() > 0);
    }

    @DisplayName("должен выдавать корректный идентификатор новой книги при вставке")
    @Test
    void shouldReturnCorrectBookIdWhenInsertedNew() {
        val book = new Book(0, "Test", new Author(1,null), new Genre(1,null));

        val saved = bookRepository.save(book);
        assertThat(saved).isNotNull().matches(b -> b.getId() > 0);
    }

    @DisplayName("должен изменить книгу при обновлении")
    @Test
    void shouldCorrectUpdateBook() {
        val book = new Book(1,"Test", new Author(1,null), new Genre(1,null));

        val updated = bookRepository.save(book);

        assertThat(updated).isNotNull()
                .matches(b -> b.getId() == 1)
                .matches(b -> b.getAuthor().getId() == 1)
                .matches(b -> b.getGenre().getId() == 1);
    }

    @DisplayName("должен выдавать книгу по существующему идентификатору")
    @Test
    void shouldReturnBookWhenIdExists() {
        val book = bookRepository.findById(1L);

        assertThat(book).isNotEmpty().get()
                .matches(b -> b.getId() == 1)
                .matches(b -> b.getAuthor().getId() == 2)
                .matches(b -> b.getGenre().getId() == 1);
    }

    @DisplayName("должен выдавать Empty по несуществующему идентификатору")
    @Test
    void shouldReturnEmptyWhenIdNotExists() {
        val book = bookRepository.findById(-1L);

        assertThat(book).isEmpty();
    }

    @DisplayName("должен удалять книгу по существующему идентификатору")
    @Test
    void shouldDeleteBookWhenIdExists() {
        val id = 1L;

        bookRepository.deleteById(id);

        assertThat(entityManager.find(Book.class, id)).isNull();
    }
}
