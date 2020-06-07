package ru.otus.hw02.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.hw02.dao.QuestionDao;
import ru.otus.hw02.domain.UserTestSettings;
import ru.otus.hw02.service.QuestionsService;
import ru.otus.hw02.service.StudentTestingService;
import ru.otus.hw02.service.impl.ConsoleStudentTestingService;
import ru.otus.hw02.service.impl.QuestionsServiceImpl;

@Data
@Configuration
@PropertySource("classpath:app.properties")
public class ApplicationConfig {

    @Value("${questions.filepath}")
    private String questionsFileName = "questions.csv";

    @Value("${questionsCount}")
    private int questionsCount = 1;
    @Value("${minCorrectAnswersForCredit}")
    private int minCorrectAnswersForCredit = 1;

    @Bean
    QuestionDao questionDao() {
        return new QuestionDao(questionsFileName);
    }

    @Bean
    QuestionsService questionsService() {
        return new QuestionsServiceImpl(questionDao());
    }

    @Bean
    StudentTestingService userInterfaceService() {
        UserTestSettings userTestSettings = new UserTestSettings(questionsCount, minCorrectAnswersForCredit);
        return new ConsoleStudentTestingService(questionsService(),userTestSettings);
    }
}
