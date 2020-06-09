package ru.otus.hw02.dao;

import ru.otus.hw02.domain.Question;

import java.io.IOException;
import java.util.List;

public interface QuestionDao {
    List<Question> getAll() throws IOException;
}
