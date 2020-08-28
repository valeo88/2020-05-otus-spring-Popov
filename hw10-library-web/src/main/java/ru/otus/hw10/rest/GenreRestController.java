package ru.otus.hw10.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw10.model.Genre;
import ru.otus.hw10.service.GenreService;

import java.util.List;

@RestController
public class GenreRestController {

    private final GenreService genreService;

    public GenreRestController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/api/genre")
    public List<Genre> getAll() {
        return genreService.getAll();
    }
}
