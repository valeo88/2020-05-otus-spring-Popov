package ru.otus.hw04.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Locale;

@ConfigurationProperties(prefix = "application")
@Data
@Component
public class ApplicationConfig {
    private Locale locale = Locale.ENGLISH;
}
