package ru.otus.hw04.dao;

import ru.otus.hw04.domain.Question;
import ru.otus.hw04.exception.QuestionsLoadException;

import java.util.List;

public interface QuestionDao {
    List<Question> getAll() throws QuestionsLoadException;
}
