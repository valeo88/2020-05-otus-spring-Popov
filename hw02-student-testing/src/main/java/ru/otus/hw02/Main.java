package ru.otus.hw02;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.hw02.service.UserInterfaceService;

@ComponentScan
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(Main.class);

        UserInterfaceService userInterfaceService = context.getBean(UserInterfaceService.class);
        userInterfaceService.test();

        context.close();
    }
}
