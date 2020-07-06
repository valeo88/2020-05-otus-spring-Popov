package ru.otus.hw05.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.hw05.dao.AuthorDao;
import ru.otus.hw05.model.Author;
import ru.otus.hw05.service.AuthorService;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;

    public AuthorServiceImpl(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @Override
    public List<Author> getAll() {
        return authorDao.getAll();
    }
}
