package ru.otus.hw06.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.hw06.model.Author;
import ru.otus.hw06.repository.AuthorRepository;
import ru.otus.hw06.service.AuthorService;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Author> getAll() {
        return authorRepository.getAll();
    }
}
