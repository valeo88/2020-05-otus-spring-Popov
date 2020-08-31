package ru.otus.hw10.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw10.model.Author;
import ru.otus.hw10.service.AuthorService;

import java.util.List;

@RestController
public class AuthorRestController {

    private final AuthorService authorService;

    public AuthorRestController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/api/author")
    public List<Author> getAll() {
        return authorService.getAll();
    }
}
