package ru.otus.hw01;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.hw01.service.QuestionsService;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        QuestionsService questionsService = context.getBean(QuestionsService.class);
        questionsService.printAll(System.out);
        context.close();
    }
}
