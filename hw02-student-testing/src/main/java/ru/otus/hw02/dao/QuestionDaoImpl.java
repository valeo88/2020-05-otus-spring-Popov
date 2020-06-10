package ru.otus.hw02.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;
import ru.otus.hw02.domain.AnswerVariant;
import ru.otus.hw02.domain.Question;
import ru.otus.hw02.domain.QuestionType;
import ru.otus.hw02.exception.QuestionsLoadException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class QuestionDaoImpl implements QuestionDao {

    private final Resource data;

    public QuestionDaoImpl(@Value("${questions.filepath}") String filePath) {
        this.data = new ClassPathResource(filePath);
    }

    public List<Question> getAll() throws QuestionsLoadException {
        if (data.exists() && data.isReadable()) {
            try ( BufferedReader reader = new BufferedReader(
                    new InputStreamReader(data.getInputStream())) ) {
                return reader.lines()
                        .skip(1)
                        .map(this::parseQuestion)
                        .collect(Collectors.toList());
            } catch (IOException e) {
                throw new QuestionsLoadException("Error on load questions", e);
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
