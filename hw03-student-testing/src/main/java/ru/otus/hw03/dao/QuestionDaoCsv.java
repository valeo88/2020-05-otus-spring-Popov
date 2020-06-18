package ru.otus.hw03.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;
import ru.otus.hw03.config.ApplicationConfig;
import ru.otus.hw03.domain.AnswerVariant;
import ru.otus.hw03.domain.Question;
import ru.otus.hw03.domain.QuestionType;
import ru.otus.hw03.exception.QuestionsLoadException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class QuestionDaoCsv implements QuestionDao {
    private static final String FILE_EXTENSION = ".csv";

    private final Optional<Resource> data;
    private final ApplicationConfig applicationConfig;

    public QuestionDaoCsv(@Value("${questions.filepath}") String filePath,
                          ApplicationConfig applicationConfig) {
        this.data = getLocalizedResource(filePath, applicationConfig.getLocale());
        this.applicationConfig = applicationConfig;
    }

    public List<Question> getAll() throws QuestionsLoadException {
        if (data.isPresent() && data.get().exists() && data.get().isReadable()) {
            try ( BufferedReader reader = new BufferedReader(
                    new InputStreamReader(data.get().getInputStream())) ) {
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

    private Optional<Resource> getLocalizedResource(String filePath, Locale locale) {
        List<String> pathsToCheck = new ArrayList<>();
        pathsToCheck.add(filePath.replace(FILE_EXTENSION, String.format("_%s_%s%s", locale.getLanguage(), locale.getCountry(), FILE_EXTENSION)));
        pathsToCheck.add(filePath.replace(FILE_EXTENSION, String.format("_%s%s", locale.getLanguage(), FILE_EXTENSION)));
        pathsToCheck.add(filePath);
        return pathsToCheck.stream().map(path -> new ClassPathResource(path))
                .map(classPathResource -> (Resource) classPathResource)
                .filter(Resource::exists)
                .findFirst();
    }
}
