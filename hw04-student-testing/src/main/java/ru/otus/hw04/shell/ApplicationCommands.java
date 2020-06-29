package ru.otus.hw04.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw04.service.StudentTestingService;

@ShellComponent
public class ApplicationCommands {

    private final StudentTestingService studentTestingService;

    public ApplicationCommands(StudentTestingService studentTestingService) {
        this.studentTestingService = studentTestingService;
    }

    @ShellMethod(key = "start-test", value = "Start test for current user")
    public void startTest() {
        studentTestingService.test();
    }
}
