package ru.otus.hw02.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hw02.domain.Question;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("QuestionDao test")
class QuestionDaoTest {
    private static final String correctFilePath = "questions.csv";
    private static final String incorrectFilePath = "notexist.csv";

    @DisplayName("Test loading questions from correct file")
    @Test
    void shouldReturnQuestionsIfResourceCorrect() throws IOException {
        QuestionDao questionDao = new QuestionDaoImpl(correctFilePath);

        List<Question> questions = questionDao.getAll();

        assertEquals(5, questions.size());
    }

    @DisplayName("Test loading questions from incorrect file")
    @Test
    void shouldReturnEmptyListIfResourceInCorrect() throws IOException {
        QuestionDao questionDao = new QuestionDaoImpl(incorrectFilePath);

        List<Question> questions = questionDao.getAll();

        assertTrue(questions.isEmpty());
    }
}