package ru.otus.hw03.dao;

import ru.otus.hw03.domain.Question;
import ru.otus.hw03.exception.QuestionsLoadException;

import java.util.List;

public interface QuestionDao {
    List<Question> getAll() throws QuestionsLoadException;
}
