package ru.otus.hw02.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.hw02.dao.QuestionDao;
import ru.otus.hw02.service.QuestionsService;
import ru.otus.hw02.service.UserInterfaceService;
import ru.otus.hw02.service.impl.ConsoleUserInterfaceService;
import ru.otus.hw02.service.impl.QuestionsServiceImpl;

@Data
@Configuration
@PropertySource("classpath:app.properties")
public class ApplicationConfig {

    @Value("${questions.filepath}")
    private String questionsFileName = "questions.csv";

    @Value("${minCorrectAnswers}")
    private int minCorrectAnswers = 1;

    @Bean
    QuestionDao questionDao() {
        return new QuestionDao(questionsFileName);
    }

    @Bean
    QuestionsService questionsService() {
        return new QuestionsServiceImpl(questionDao());
    }

    @Bean
    UserInterfaceService userInterfaceService() {
        return new ConsoleUserInterfaceService(questionsService());
    }
}
