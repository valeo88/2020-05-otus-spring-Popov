package ru.otus.hw05.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.hw05.model.Author;
import ru.otus.hw05.model.Book;
import ru.otus.hw05.model.Genre;
import ru.otus.hw05.service.BookService;

import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
public class ApplicationCommands {

    private final BookService bookService;

    public ApplicationCommands(BookService bookService) {
        this.bookService = bookService;
    }

    @ShellMethod(key = "create-book", value = "Create new book")
    public String createBook(@ShellOption({"--name"}) String name,  @ShellOption({"--author"}) String authorName,
                           @ShellOption({"--genre"}) String genreName) {
        Genre genre = new Genre(genreName);
        Author author = new Author(authorName);
        Book book = new Book(name,author,genre);
        bookService.save(book);
        return book.toString();
    }

    @ShellMethod(key = "list-books", value = "List all books")
    public String listBooks() {
        List<Book> bookList = bookService.getAll();
        return bookList.stream().map(Book::toString).collect(Collectors.joining("\n"));
    }
}
