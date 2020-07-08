package ru.otus.hw06.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.hw06.model.Genre;
import ru.otus.hw06.repository.GenreRepository;
import ru.otus.hw06.service.GenreService;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public List<Genre> getAll() {
        return genreRepository.getAll();
    }
}
