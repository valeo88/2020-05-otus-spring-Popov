package ru.otus.hw01.service;

import ru.otus.hw01.dao.QuestionDao;

import java.io.IOException;
import java.io.OutputStream;

public class QuestionsServiceImpl implements QuestionsService {
    private static final String QUESTION_DIVIDER = "-----------------------------------------";

    private final QuestionDao dao;
    private final PrintService printService;

    public QuestionsServiceImpl(QuestionDao dao, PrintService printService) {
        this.dao = dao;
        this.printService = printService;
    }

    @Override
    public void printAll(OutputStream out) {
        try {
            dao.getAll().forEach(question -> {
                printService.println(String.format("Question %d (%s): %s", question.getNumber(),
                        question.getType().getValue(), question.getText()));
                question.getAnswerVariants().forEach(answerVariant -> {
                    printService.println(String.format("%d) %s", answerVariant.getNumber(), answerVariant.getText()));
                });
                printService.println(QUESTION_DIVIDER);
            });
        } catch (IOException e) {
            printService.printError("Error on loading questions: " + e.getMessage());
        }
    }
}
