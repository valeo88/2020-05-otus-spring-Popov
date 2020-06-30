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
    public void createBook(@ShellOption({"--id"}) long id, @ShellOption({"--name"}) String name) {
        Author author = new Author(1,"","");
        Genre genre = new Genre(1,"");
        Book book = new Book(id,name,author,genre);
        bookService.save(book);
    }

    @ShellMethod(key = "list-books", value = "List all books")
    public String listBooks() {
        List<Book> bookList = bookService.getAll();
        return bookList.stream().map(book -> book.toString()).collect(Collectors.joining("\n"));
    }
}
