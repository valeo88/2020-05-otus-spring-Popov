package ru.otus.hw08.init;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import ru.otus.hw08.model.Author;
import ru.otus.hw08.model.Book;
import ru.otus.hw08.model.Genre;

import java.util.ArrayList;
import java.util.List;

@Component
public class InitMongoDBData {

    private List<Genre> genres = new ArrayList<>();
    private List<Author> authors = new ArrayList<>();

    private final MongoTemplate template;

    public InitMongoDBData(MongoTemplate template) {
        this.template = template;
        initGenres();
        initAuthors();
        initBooks();
    }

    private void initGenres(){
        genres.add(template.save(new Genre("genre-1", "Mystic")));
        genres.add(template.save(new Genre("genre-2", "Fantasy")));
    }

    private void initAuthors(){
        authors.add(template.save(new Author("author-1", "Ivan Ivanov")));
        authors.add(template.save(new Author("author-2", "Petr Petrov")));
    }

    private void initBooks(){
        template.save(new Book("book-1", "Hello Python", authors.get(0), genres.get(1)));
        template.save(new Book("book-2", "Hello Java", authors.get(1), genres.get(1)));
        template.save(new Book("book-3", "Bye Python", authors.get(1), genres.get(0)));
        template.save(new Book("book-4", "Hello Kotlin", authors.get(0), genres.get(0)));
    }
}
