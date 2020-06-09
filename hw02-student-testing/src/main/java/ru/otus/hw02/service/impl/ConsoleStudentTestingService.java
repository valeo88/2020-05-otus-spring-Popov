package ru.otus.hw02.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.hw02.config.UserTestSettings;
import ru.otus.hw02.domain.Question;
import ru.otus.hw02.service.QuestionsService;
import ru.otus.hw02.service.StudentTestingService;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ConsoleStudentTestingService implements StudentTestingService {
    private static final String QUESTION_DIVIDER = "-----------------------------------------";

    private final PrintStream out = new PrintStream(System.out);
    private final PrintStream err = new PrintStream(System.err);
    private final Scanner scanner = new Scanner(System.in);

    private final QuestionsService questionsService;
    private final UserTestSettings userTestSettings;

    public ConsoleStudentTestingService(QuestionsService questionsService,
                                        UserTestSettings userTestSettings) {
        this.questionsService = questionsService;
        this.userTestSettings = userTestSettings;
    }

    @Override
    public void showAllQuestions() {
        try {
            questionsService.getAll().forEach(question -> {
                printQuestionWithAnswers(question);
                out.println(QUESTION_DIVIDER);
            });
        } catch (Exception e) {
            err.println("Error on loading questions: " + e.getMessage());
        }
    }

    @Override
    public void test() {
        out.println("What's your name and surname?");
        String userName = scanner.nextLine();
        out.println("Ok, let's start testing!");
        AtomicInteger correctAnswersCount = new AtomicInteger(0);
        List<Question> allQuestions;
        try {
            allQuestions = questionsService.getAll();
        } catch (Exception e) {
            err.println("Error on loading questions: " + e.getMessage());
            return;
        }
        allQuestions.stream()
                .limit(userTestSettings.getQuestionsCount())
                .forEach(question -> {
                    printQuestionWithAnswers(question);
                    if (checkUserAnswer(question)) {
                        correctAnswersCount.incrementAndGet();
                    }
                });
        printResults(userName, correctAnswersCount.get());
    }

    private void printQuestionWithAnswers(Question question) {
        out.println(String.format("Question %d (%s): %s", question.getNumber(),
                question.getType().getValue(), question.getText()));
        question.getAnswerVariants().forEach(answerVariant -> {
            out.println(String.format("%d) %s", answerVariant.getNumber(), answerVariant.getText()));
        });
    }

    private boolean checkUserAnswer(Question question) {
        out.println("Please write your answer.");
        String answer = scanner.nextLine();
        return questionsService.isAnswerCorrect(question, answer);
    }

    private void printResults(String userName, int correctAnswers) {
        if (correctAnswers >= userTestSettings.getMinCorrectAnswersForCredit()) {
            out.println("Congratulations to " + userName + " for test pass!");
        } else {
            out.println(userName + " please try again :(");
        }
    }
}
