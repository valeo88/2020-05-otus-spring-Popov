package ru.otus.hw02.domain;

public enum QuestionType {
    CHOICE("choice"), // выбор из списка ответов
    INPUT("input"); // свободный ввод

    private final String value;

    QuestionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static QuestionType fromString(String value) {
        for (QuestionType questionType : values()) {
            if (questionType.getValue().equals(value)) {
                return questionType;
            }
        }
        throw new IllegalArgumentException("No enum found for value: " + value);
    }
}
