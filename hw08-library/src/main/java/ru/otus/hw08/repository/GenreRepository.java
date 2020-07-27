package ru.otus.hw08.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.hw08.model.Genre;

public interface GenreRepository extends MongoRepository<Genre, String> {
}
