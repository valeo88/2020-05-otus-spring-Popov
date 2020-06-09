package ru.otus.hw02;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import ru.otus.hw02.service.StudentTestingService;

@PropertySource("classpath:app.properties")
@ComponentScan
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(Main.class);

        StudentTestingService studentTestingService = context.getBean(StudentTestingService.class);
        studentTestingService.test();

        context.close();
    }
}
