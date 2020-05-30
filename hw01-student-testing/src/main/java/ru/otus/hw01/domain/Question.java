package ru.otus.hw01.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Question {
    private int number;
    private QuestionType type;
    private String text;
    private List<AnswerVariant> answerVariants = new ArrayList<>();
}
