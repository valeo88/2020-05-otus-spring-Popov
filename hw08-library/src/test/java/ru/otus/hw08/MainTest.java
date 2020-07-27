package ru.otus.hw08;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("В приложении на Spring Boot c отключенным Spring Shell ")
@SpringBootTest
public class MainTest {

    @Autowired
    private ApplicationContext applicationContext;

    @DisplayName(" applicationContext должен запустится")
    @Test
    void contextShouldStart() {
         assertThat(applicationContext).isNotNull();
    }
}
