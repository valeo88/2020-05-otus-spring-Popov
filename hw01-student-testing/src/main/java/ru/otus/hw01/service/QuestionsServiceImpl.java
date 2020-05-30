package ru.otus.hw01.service;

import ru.otus.hw01.dao.QuestionDao;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class QuestionsServiceImpl implements QuestionsService {

    private final QuestionDao dao;

    public QuestionsServiceImpl(QuestionDao dao) {
        this.dao = dao;
    }

    @Override
    public void printAll(OutputStream out) {
        String divider = IntStream.range(0, 20).mapToObj(i -> "-").collect(Collectors.joining(""));
        try (PrintStream printStream = new PrintStream(out)) {
            dao.getAll().forEach(question -> {
                printStream.println(String.format("Question %d (%s): %s", question.getNumber(),
                        question.getType().getValue(), question.getText()));
                question.getAnswerVariants().forEach(answerVariant -> {
                        printStream.println(String.format("%d) %s", answerVariant.getNumber(), answerVariant.getText()));
                });
                printStream.println(divider);
            });
        } catch (IOException e) {
            System.err.println("Error on loading questions...");
        }
    }
}
