package ru.otus.hw03.service.impl;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.hw03.config.UserTestSettings;
import ru.otus.hw03.domain.Question;
import ru.otus.hw03.service.IOService;
import ru.otus.hw03.service.QuestionsService;
import ru.otus.hw03.service.StudentTestingService;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ConsoleStudentTestingService implements StudentTestingService {
    private static final String QUESTION_DIVIDER = "-----------------------------------------";

    private final MessageSource messageSource;
    private final QuestionsService questionsService;
    private final UserTestSettings userTestSettings;
    private final IOService ioService;

    public ConsoleStudentTestingService(MessageSource messageSource,
                                        QuestionsService questionsService,
                                        UserTestSettings userTestSettings,
                                        IOService ioService) {
        this.messageSource = messageSource;
        this.questionsService = questionsService;
        this.userTestSettings = userTestSettings;
        this.ioService = ioService;
    }

    @Override
    public void showAllQuestions() {
        try {
            questionsService.getAll().forEach(question -> {
                printQuestionWithAnswers(question);
                ioService.writeText(QUESTION_DIVIDER);
            });
        } catch (Exception e) {
            ioService.writeError(messageSource.getMessage("error.loadingQuestions", new String[]{e.getMessage()}, userTestSettings.getLocale()));
        }
    }

    @Override
    public void test() {
        String userName = getUserName();
        int correctAnswersCount = performTesting();
        printResults(userName, correctAnswersCount);
    }

    private String getUserName() {
        ioService.writeText(messageSource.getMessage("question.whatsYourNameAndSurname", new String[]{}, userTestSettings.getLocale()));
        return ioService.readText();
    }

    private int performTesting() {
        ioService.writeText("Ok, let's start testing!");
        AtomicInteger correctAnswersCount = new AtomicInteger(0);
        List<Question> allQuestions;
        try {
            allQuestions = questionsService.getAll();
        } catch (Exception e) {
            ioService.writeText("Error on loading questions: " + e.getMessage());
            return 0;
        }
        allQuestions.stream()
                .limit(userTestSettings.getQuestionsCount())
                .forEach(question -> {
                    printQuestionWithAnswers(question);
                    if (checkUserAnswer(question)) {
                        correctAnswersCount.incrementAndGet();
                    }
                });
        return correctAnswersCount.get();
    }

    private void printQuestionWithAnswers(Question question) {
        ioService.writeText(String.format("Question %d (%s): %s", question.getNumber(),
                question.getType().getValue(), question.getText()));
        question.getAnswerVariants().forEach(answerVariant -> {
            ioService.writeText(String.format("%d) %s", answerVariant.getNumber(), answerVariant.getText()));
        });
    }

    private boolean checkUserAnswer(Question question) {
        ioService.writeText("Please write your answer.");
        String answer = ioService.readText();
        return questionsService.isAnswerCorrect(question, answer);
    }

    private void printResults(String userName, int correctAnswers) {
        if (correctAnswers >= userTestSettings.getMinCorrectAnswersForCredit()) {
            ioService.writeText("Congratulations to " + userName + " for test pass!");
        } else {
            ioService.writeText(userName + " please try again :(");
        }
    }
}
