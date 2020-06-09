package ru.otus.hw02.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/** Настройки тестирования для пользователя. */
@Data
@Component
public class UserTestSettings {
    @Value("${questionsCount}")
    private int questionsCount = 1;

    @Value("${minCorrectAnswersForCredit}")
    private int minCorrectAnswersForCredit = 1;
}
