package ru.otus.hw11.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.hw11.model.Book;

public interface BookRepository extends ReactiveMongoRepository<Book, String> {
}
