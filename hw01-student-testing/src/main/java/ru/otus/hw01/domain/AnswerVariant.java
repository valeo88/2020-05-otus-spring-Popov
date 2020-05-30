package ru.otus.hw01.domain;

import lombok.Data;

@Data
public class AnswerVariant {
    private int number;
    private String text;
    private boolean isCorrect;
}
