package ru.otus.hw02.domain;

import lombok.Value;

/** Настройки тестирования для пользователя. */
@Value
public class UserTestSettings {
    int questionsCount;
    int minCorrectAnswersForCredit;
}
