package ru.otus.hw04.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.hw04.dao.QuestionDao;
import ru.otus.hw04.domain.Question;
import ru.otus.hw04.service.QuestionsService;

import java.util.List;

@Service
public class QuestionsServiceImpl implements QuestionsService {

    private final QuestionDao dao;

    public QuestionsServiceImpl(QuestionDao dao) {
        this.dao = dao;
    }

    @Override
    public List<Question> getAll() {
        return dao.getAll();
    }

    @Override
    public boolean isAnswerCorrect(Question question, String answer) {
        switch (question.getType()) {
            case INPUT:
                return !answer.isEmpty();
            case CHOICE:
                return !answer.isEmpty() && question.getAnswerVariants().stream()
                        .anyMatch(answerVariant -> answerVariant.isCorrect()
                                && answerVariant.getNumber() == parseAnswerNumber(answer));
            default:
                return false;
        }
    }

    private int parseAnswerNumber(String answer) {
        try {
            return Integer.valueOf(answer);
        } catch (Exception ignored) {
            return Integer.MIN_VALUE;
        }
    }
}
