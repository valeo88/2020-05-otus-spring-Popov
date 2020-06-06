package ru.otus.hw02.service.impl;

import ru.otus.hw02.service.QuestionsService;
import ru.otus.hw02.service.UserInterfaceService;

import java.io.PrintStream;
import java.util.Scanner;

public class ConsoleUserInterfaceService implements UserInterfaceService {
    private static final String QUESTION_DIVIDER = "-----------------------------------------";

    private final PrintStream out = new PrintStream(System.out);
    private final PrintStream err = new PrintStream(System.err);
    private final Scanner scanner = new Scanner(System.in);

    private final QuestionsService questionsService;

    public ConsoleUserInterfaceService(QuestionsService questionsService) {
        this.questionsService = questionsService;
    }

    @Override
    public void showAllQuestions() {
        try {
            questionsService.getAll().forEach(question -> {
                out.println(String.format("Question %d (%s): %s", question.getNumber(),
                        question.getType().getValue(), question.getText()));
                question.getAnswerVariants().forEach(answerVariant -> {
                    out.println(String.format("%d) %s", answerVariant.getNumber(), answerVariant.getText()));
                });
                out.println(QUESTION_DIVIDER);
            });
        } catch (Exception e) {
            err.println("Error on loading questions: " + e.getMessage());
        }
    }

    @Override
    public String getUserName() {
        return null;
    }
}
