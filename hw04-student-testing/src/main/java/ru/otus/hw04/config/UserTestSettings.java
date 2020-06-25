package ru.otus.hw04.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/** Настройки тестирования для пользователя. */
@ConfigurationProperties(prefix = "testing-settings")
@Data
@Component
public class UserTestSettings {
    private int questionsCount = 1;
    private int minCorrectAnswersForCredit = 1;
}
