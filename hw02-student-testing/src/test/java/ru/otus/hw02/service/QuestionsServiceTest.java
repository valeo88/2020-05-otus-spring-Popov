package ru.otus.hw02.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hw02.dao.QuestionDao;
import ru.otus.hw02.domain.AnswerVariant;
import ru.otus.hw02.domain.Question;
import ru.otus.hw02.domain.QuestionType;
import ru.otus.hw02.service.impl.QuestionsServiceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("QuestionsService test")
class QuestionsServiceTest {

    private QuestionDao questionDao = mock(QuestionDao.class);
    private QuestionsService questionsService;

    @BeforeEach
    void setUp() throws IOException {
        when(questionDao.getAll()).thenReturn(
                sampleQuestions()
        );

        questionsService = new QuestionsServiceImpl(questionDao);
    }

    @DisplayName("Test return full questions list")
    @Test
    void shouldReturnQuestionsList() {
        List<Question> questions = questionsService.getAll();

        assertEquals(2, questions.size());
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

    private List<Question> sampleQuestions() {
        List<Question> questions = new ArrayList<>();
        Question question1 = new Question();
        question1.setType(QuestionType.CHOICE);
        question1.setText("1");
        AnswerVariant answerVariant = new AnswerVariant();
        answerVariant.setNumber(1);
        answerVariant.setText("any");
        answerVariant.setCorrect(true);
        question1.setAnswerVariants(List.of(answerVariant));
        questions.add(question1);
        Question question2 = new Question();
        question2.setType(QuestionType.INPUT);
        question2.setText("2");
        questions.add(question2);

        return questions;
    }
}