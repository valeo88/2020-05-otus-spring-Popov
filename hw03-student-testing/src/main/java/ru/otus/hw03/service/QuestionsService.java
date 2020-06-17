package ru.otus.hw03.service;

import ru.otus.hw03.domain.Question;

import java.util.List;

public interface QuestionsService {
    List<Question> getAll();
    boolean isAnswerCorrect(Question question, String answer);
}
