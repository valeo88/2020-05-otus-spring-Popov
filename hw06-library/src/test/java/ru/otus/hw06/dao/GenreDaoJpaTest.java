package ru.otus.hw06.dao;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DAO на основе JPA для работы с жанрами ")
@DataJpaTest
@Import({GenreDaoJpa.class})
public class GenreDaoJpaTest {

    private static final int EXPECTED_NUMBER_OF_GENRES = 2;

    @Autowired
    private GenreDaoJpa genreDao;

    @DisplayName("должен загружать список всех жанров с полной информацией о них")
    @Test
    void shouldReturnCorrectGenresListWithAllInfo() {
        val genres = genreDao.getAll();
        assertThat(genres).isNotNull().hasSize(EXPECTED_NUMBER_OF_GENRES)
                .allMatch(a -> !a.getName().equals(""))
                .allMatch(a -> a.getId() > 0);
    }
}
