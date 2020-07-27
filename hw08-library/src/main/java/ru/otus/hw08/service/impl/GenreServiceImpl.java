package ru.otus.hw08.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw08.model.Genre;
import ru.otus.hw08.repository.GenreRepository;
import ru.otus.hw08.service.GenreService;

import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Genre> getById(String id) {
        return genreRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Genre> getAll() {
        return genreRepository.findAll();
    }
}
