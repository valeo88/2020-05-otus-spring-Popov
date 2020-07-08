package ru.otus.hw06.dao;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.hw06.model.Author;
import ru.otus.hw06.model.Book;
import ru.otus.hw06.model.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DAO на основе JPA для работы с книгами ")
@DataJpaTest
@Import({BookDaoJpa.class})
public class BookDaoJpaTest {

    private static final int EXPECTED_NUMBER_OF_BOOKS = 4;

    @Autowired
    private BookDaoJpa bookDao;

    @DisplayName("должен выдавать корректное количество книг")
    @Test
    void shouldReturnCorrectCountOfAllBooks() {
        val count = bookDao.count();
        assertThat(count).isEqualTo(EXPECTED_NUMBER_OF_BOOKS);
    }

    @DisplayName("должен загружать список всех книг с полной информацией о них")
    @Test
    void shouldReturnCorrectGenresListWithAllInfo() {
        val books = bookDao.getAll();
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

        val bookId = bookDao.insert(book);
        assertThat(bookId).isGreaterThan(0);
    }

    @DisplayName("должен изменить книгу при обновлении")
    @Test
    void shouldCorrectUpdateBook() {
        val book = new Book(1,"Test", new Author(1,null), new Genre(1,null));

        bookDao.update(book);

        val updated = bookDao.getById(book.getId());
        assertThat(updated).isNotEmpty()
                .matches(b -> b.get().getId() == 1)
                .matches(b -> b.get().getAuthor().getId() == 1)
                .matches(b -> b.get().getGenre().getId() == 1);
    }

    @DisplayName("должен выдавать книгу по существующему идентификатору")
    @Test
    void shouldReturnBookWhenIdExists() {
        val book = bookDao.getById(1);

        assertThat(book).isNotEmpty()
                .matches(b -> b.get().getId() == 1)
                .matches(b -> b.get().getAuthor().getId() == 2)
                .matches(b -> b.get().getGenre().getId() == 1);
    }

    @DisplayName("должен выдавать Empty по несуществующему идентификатору")
    @Test
    void shouldReturnEmptyWhenIdNotExists() {
        val book = bookDao.getById(-1);

        assertThat(book).isEmpty();
    }

    @DisplayName("должен удалять книгу по существующему идентификатору")
    @Test
    void shouldDeleteBookWhenIdExists() {
        val id = 1;

        bookDao.deleteById(id);

        assertThat(bookDao.getById(id)).isEmpty();
    }
}
