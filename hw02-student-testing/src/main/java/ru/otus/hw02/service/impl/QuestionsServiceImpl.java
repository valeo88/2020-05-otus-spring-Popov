package ru.otus.hw02.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.hw02.dao.QuestionDao;
import ru.otus.hw02.domain.Question;
import ru.otus.hw02.service.QuestionsLoadException;
import ru.otus.hw02.service.QuestionsService;

import java.io.IOException;
import java.util.List;

@Service
public class QuestionsServiceImpl implements QuestionsService {

    private final QuestionDao dao;

    public QuestionsServiceImpl(QuestionDao dao) {
        this.dao = dao;
    }

    @Override
    public List<Question> getAll() {
        try {
            return dao.getAll();
        } catch (IOException e) {
            throw new QuestionsLoadException("Error on loading questions.");
        }
    }
}
