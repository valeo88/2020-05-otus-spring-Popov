package ru.otus.hw07.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Spring Data для работы с авторами ")
@DataJpaTest
public class AuthorRepositoryTest {

    private static final int EXPECTED_NUMBER_OF_AUTHORS = 3;

    @Autowired
    private AuthorRepository authorRepository;

    @DisplayName("должен загружать список всех авторов с полной информацией о них")
    @Test
    void shouldReturnCorrectAuthorsListWithAllInfo() {
        val authors = authorRepository.findAll();
        assertThat(authors).isNotNull().hasSize(EXPECTED_NUMBER_OF_AUTHORS)
                .allMatch(a -> !a.getFullName().equals(""))
                .allMatch(a -> a.getId() > 0);
    }
}
