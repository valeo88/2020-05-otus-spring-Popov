package ru.otus.hw02.service;

import ru.otus.hw02.domain.Question;

import java.util.List;

public interface QuestionsService {
    List<Question> getAll();
}
