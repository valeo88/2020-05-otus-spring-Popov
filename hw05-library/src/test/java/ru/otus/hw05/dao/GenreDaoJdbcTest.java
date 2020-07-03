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

@DisplayName("DAO на основе Jdbc для работы с жанрами ")
@JdbcTest(
        properties = {
            InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
            ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
        }
)
@Import({GenreDaoJdbc.class})
public class GenreDaoJdbcTest {

    private static final int EXPECTED_NUMBER_OF_GENRES = 2;

    @Autowired
    private GenreDaoJdbc genreDao;

    @DisplayName("должен загружать список всех жанров с полной информацией о них")
    @Test
    void shouldReturnCorrectGenresListWithAllInfo() {
        val genres = genreDao.getAll();
        assertThat(genres).isNotNull().hasSize(EXPECTED_NUMBER_OF_GENRES)
                .allMatch(a -> !a.getName().equals(""))
                .allMatch(a -> a.getId() > 0);
    }
}
