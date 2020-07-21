package ru.otus.hw08.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.hw08.model.Book;

public interface BookRepository extends MongoRepository<Book, String> {
}
