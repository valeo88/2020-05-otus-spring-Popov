package ru.otus.hw04.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hw04.config.ApplicationConfig;
import ru.otus.hw04.domain.Question;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("QuestionDao test")
class QuestionDaoTest {
    private static final String correctFilePath = "questions.csv";
    private static final String incorrectFilePath = "notexist.csv";

    private ApplicationConfig applicationConfig;

    @BeforeEach
    void setUp() {
        applicationConfig = new ApplicationConfig();
    }

    @DisplayName("Test loading questions from correct file")
    @Test
    void shouldReturnQuestionsIfResourceCorrect() throws IOException {
        QuestionDao questionDao = new QuestionDaoCsv(correctFilePath, applicationConfig);

        List<Question> questions = questionDao.getAll();

        assertEquals(5, questions.size());
    }

    @DisplayName("Test loading questions from incorrect file")
    @Test
    void shouldReturnEmptyListIfResourceInCorrect() throws IOException {
        QuestionDao questionDao = new QuestionDaoCsv(incorrectFilePath, applicationConfig);

        List<Question> questions = questionDao.getAll();

        assertTrue(questions.isEmpty());
    }
}