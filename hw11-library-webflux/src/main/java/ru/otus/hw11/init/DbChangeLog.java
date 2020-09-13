package ru.otus.hw11.init;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import com.mongodb.client.MongoDatabase;
import ru.otus.hw11.model.Author;
import ru.otus.hw11.model.Book;
import ru.otus.hw11.model.Genre;

import java.util.ArrayList;
import java.util.List;

@ChangeLog(order = "001")
public class DbChangeLog {

    private List<Genre> genres = new ArrayList<>();
    private List<Author> authors = new ArrayList<>();

    @ChangeSet(order = "001", id = "init", author = "mongock")
    public void init(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "addAuthors", author = "mongock")
    public void addAuthors(MongockTemplate template) {
        authors.add(template.save(new Author("author-1", "Ivan Ivanov")));
        authors.add(template.save(new Author("author-2", "Petr Petrov")));
    }

    @ChangeSet(order = "003", id = "addGenres", author = "mongock")
    public void addGenres(MongockTemplate template) {
        genres.add(template.save(new Genre("genre-1", "Mystic")));
        genres.add(template.save(new Genre("genre-2", "Fantasy")));
    }

    @ChangeSet(order = "004", id = "addBooks", author = "mongock")
    public void addBooks(MongockTemplate template) {
        template.save(new Book("book-1", "Hello Python", authors.get(0), genres.get(1)));
        template.save(new Book("book-2", "Hello Java", authors.get(1), genres.get(1)));
        template.save(new Book("book-3", "Bye Python", authors.get(1), genres.get(0)));
        template.save(new Book("book-4", "Hello Kotlin", authors.get(0), genres.get(0)));
    }

}
