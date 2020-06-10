package ru.otus.hw02.dao;

import ru.otus.hw02.domain.Question;
import ru.otus.hw02.exception.QuestionsLoadException;

import java.util.List;

public interface QuestionDao {
    List<Question> getAll() throws QuestionsLoadException;
}
