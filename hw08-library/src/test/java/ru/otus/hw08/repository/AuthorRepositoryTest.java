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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Spring Data Mongo для работы с авторами ")
@DataMongoTest
public class AuthorRepositoryTest {

    private static final List<Author> authors = List.of(
            new Author("1", "Author1"), new Author("2", "Author2")
    );

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    private void setUp() {
        authors.forEach(mongoTemplate::save);
    }

    @AfterEach
    private void clear() {
        mongoTemplate.dropCollection(Author.class);
    }

    @DisplayName("должен загружать список всех авторов с полной информацией о них")
    @Test
    void shouldReturnCorrectAuthorsListWithAllInfo() {
        val authors = authorRepository.findAll();
        assertThat(authors).isNotNull().hasSize(authors.size())
                .allMatch(a -> !a.getFullName().equals(""))
                .allMatch(a -> a.getId()!=null);
    }
}
