package ru.otus.hw06.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.hw06.dao.GenreDao;
import ru.otus.hw06.model.Genre;
import ru.otus.hw06.service.GenreService;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;

    public GenreServiceImpl(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @Override
    public List<Genre> getAll() {
        return genreDao.getAll();
    }
}
