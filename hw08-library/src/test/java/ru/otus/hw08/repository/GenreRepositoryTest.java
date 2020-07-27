package ru.otus.hw08.repository;

import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.hw08.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Spring Data Mongo для работы с жанрами ")
@DataMongoTest
public class GenreRepositoryTest {

    private static final List<Genre> genres = List.of(
            new Genre("1", "Test1"), new Genre("2", "Test2")
    );

    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    private void setUp() {
        genres.forEach(mongoTemplate::save);
    }

    @AfterEach
    private void clear() {
        mongoTemplate.dropCollection(Genre.class);
    }

    @DisplayName("должен загружать список всех жанров с полной информацией о них")
    @Test
    void shouldReturnCorrectGenresListWithAllInfo() {
        val genres = genreRepository.findAll();
        assertThat(genres).isNotNull().hasSize(genres.size())
                .allMatch(a -> !a.getName().equals(""))
                .allMatch(a -> a.getId()!=null);
    }
}
