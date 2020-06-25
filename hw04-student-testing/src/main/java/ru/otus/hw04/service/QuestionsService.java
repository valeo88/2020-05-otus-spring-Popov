package ru.otus.hw04.service;

import ru.otus.hw04.domain.Question;

import java.util.List;

public interface QuestionsService {
    List<Question> getAll();
    boolean isAnswerCorrect(Question question, String answer);
}
