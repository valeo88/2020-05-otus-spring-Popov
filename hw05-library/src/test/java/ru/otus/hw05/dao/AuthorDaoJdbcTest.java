package ru.otus.hw05.dao;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DAO на основе Jdbc для работы с авторами ")
@JdbcTest(
        properties = {
            InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
            ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
        }
)
@Import({AuthorDaoJdbc.class})
public class AuthorDaoJdbcTest {

    private static final int EXPECTED_NUMBER_OF_AUTHORS = 3;

    @Autowired
    private AuthorDaoJdbc authorDao;

    @DisplayName("должен загружать список всех авторов с полной информацией о них")
    @Test
    void shouldReturnCorrectAuthorsListWithAllInfo() {
        val authors = authorDao.getAll();
        assertThat(authors).isNotNull().hasSize(EXPECTED_NUMBER_OF_AUTHORS)
                .allMatch(a -> !a.getFullName().equals(""))
                .allMatch(a -> a.getId() > 0);
    }
}
