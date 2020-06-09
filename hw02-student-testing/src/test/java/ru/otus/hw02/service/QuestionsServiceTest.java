package ru.otus.hw02.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hw02.dao.QuestionDaoImpl;
import ru.otus.hw02.domain.AnswerVariant;
import ru.otus.hw02.domain.Question;
import ru.otus.hw02.domain.QuestionType;
import ru.otus.hw02.service.impl.QuestionsServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("QuestionsService test")
class QuestionsServiceTest {
    private static final String questionsFilePath = "src/test/resources/questions.csv";

    private QuestionsService questionsService;

    @BeforeEach
    void setUp() {
        questionsService = new QuestionsServiceImpl(new QuestionDaoImpl(questionsFilePath));
    }

    @DisplayName("Test return full questions list")
    @Test
    void shouldReturnQuestionsList() {
        List<Question> questions = questionsService.getAll();

        assertEquals(5, questions.size());
    }

    @DisplayName("Test nonempty answer in input question type")
    @Test
    void shouldBeCorrectInputAnswerOnAnyNonEmptyString() {
        Question question = new Question();
        question.setType(QuestionType.INPUT);

        boolean check = questionsService.isAnswerCorrect(question, "OK");

        assertTrue(check);
    }

    @DisplayName("Test correct answer in choice question type")
    @Test
    void shouldBeCorrectChoiceAnswerOnCorrectString() {
        Question question = new Question();
        question.setType(QuestionType.CHOICE);
        AnswerVariant correct = new AnswerVariant();
        correct.setNumber(1);
        correct.setText("Any");
        correct.setCorrect(true);
        question.setAnswerVariants(new ArrayList<>());
        question.getAnswerVariants().add(correct);

        boolean check = questionsService.isAnswerCorrect(question, "1");

        assertTrue(check);
    }

    @DisplayName("Test empty answer in any question type")
    @Test
    void shouldBeIncorrectAnswerOnEmptyString() {
        Question question = new Question();
        question.setType(QuestionType.INPUT);

        boolean checkInput = questionsService.isAnswerCorrect(question, "");
        question.setType(QuestionType.CHOICE);
        boolean checkChoice = questionsService.isAnswerCorrect(question, "");

        assertFalse(checkInput);
        assertFalse(checkChoice);
    }
}