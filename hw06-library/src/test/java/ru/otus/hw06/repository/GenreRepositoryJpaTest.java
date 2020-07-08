package ru.otus.hw06.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе JPA для работы с жанрами ")
@DataJpaTest
@Import({GenreRepositoryJpa.class})
public class GenreRepositoryJpaTest {

    private static final int EXPECTED_NUMBER_OF_GENRES = 2;

    @Autowired
    private GenreRepositoryJpa genreRepository;

    @DisplayName("должен загружать список всех жанров с полной информацией о них")
    @Test
    void shouldReturnCorrectGenresListWithAllInfo() {
        val genres = genreRepository.getAll();
        assertThat(genres).isNotNull().hasSize(EXPECTED_NUMBER_OF_GENRES)
                .allMatch(a -> !a.getName().equals(""))
                .allMatch(a -> a.getId() > 0);
    }
}
