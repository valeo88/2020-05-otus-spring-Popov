package ru.otus.hw01.dao;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import ru.otus.hw01.domain.AnswerVariant;
import ru.otus.hw01.domain.Question;
import ru.otus.hw01.domain.QuestionType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QuestionDao {
    private final Resource data;

    public QuestionDao(String fileName) {
        this.data = new ClassPathResource(fileName);
    }

    public List<Question> getAll() throws IOException {
        if (data.exists() && data.isReadable()) {
            try ( BufferedReader reader = new BufferedReader(
                    new InputStreamReader(data.getInputStream())) ) {
                return reader.lines()
                        .skip(1)
                        .map(this::parseQuestion)
                        .collect(Collectors.toList());
            }
        }
        return new ArrayList<>();
    }

    private Question parseQuestion(String source) {
        String[] parts = source.split(";");
        Question question = new Question();
        try {
            question.setNumber(Integer.parseInt(parts[0]));
            question.setType(QuestionType.fromString(parts[1]));
            question.setText(parts[2]);
            if (QuestionType.CHOICE.equals(question.getType())) {
                List<AnswerVariant> answerVariants = Stream.of(parts[3].split("\\|"))
                        .map(this::parseAnswerVariant).collect(Collectors.toList());
                question.setAnswerVariants(answerVariants);
            }
        } catch (Exception e) {
            throw new QuestionParseException("Error on parsing question", e);
        }
        return question;
    }

    private AnswerVariant parseAnswerVariant(String source) {
        String[] parts = source.split(",");
        AnswerVariant answerVariant = new AnswerVariant();
        answerVariant.setNumber(Integer.parseInt(parts[0]));
        answerVariant.setText(parts[1]);
        answerVariant.setCorrect("true".equals(parts[2]));
        return answerVariant;
    }
}
