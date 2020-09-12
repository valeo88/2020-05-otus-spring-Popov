package ru.otus.hw11.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.otus.hw11.model.Genre;
import ru.otus.hw11.service.GenreService;

@RestController
public class GenreRestController {

    private final GenreService genreService;

    public GenreRestController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/api/genre")
    public Flux<Genre> getAll() {
        return genreService.getAll();
    }
}
