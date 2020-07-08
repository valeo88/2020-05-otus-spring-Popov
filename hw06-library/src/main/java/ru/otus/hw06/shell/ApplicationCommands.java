package ru.otus.hw06.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.hw06.model.Author;
import ru.otus.hw06.model.Book;
import ru.otus.hw06.model.Genre;
import ru.otus.hw06.service.AuthorService;
import ru.otus.hw06.service.BookService;
import ru.otus.hw06.service.GenreService;

import java.util.List;
import java.util.stream.Collectors;

@ShellComponent
public class ApplicationCommands {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

    public ApplicationCommands(BookService bookService, AuthorService authorService, GenreService genreService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @ShellMethod(key = "list-authors", value = "List all available authors")
    public String listAuthors() {
        return authorService.getAll().stream().map(Author::toString).collect(Collectors.joining("\n"));
    }

    @ShellMethod(key = "list-genres", value = "List all available genres")
    public String listGenres() {
        return genreService.getAll().stream().map(Genre::toString).collect(Collectors.joining("\n"));
    }

    @ShellMethod(key = "list-books", value = "List all books")
    public String listBooks() {
        List<Book> bookList = bookService.getAll();
        return bookList.stream().map(Book::toString).collect(Collectors.joining("\n"));
    }

    @ShellMethod(key = "create-book", value = "Create new book")
    public String createBook(@ShellOption({"--name"}) String name,
                             @ShellOption({"--author-id"}) long authorId, @ShellOption({"--genre-id"}) long genreId) {
        Genre genre = new Genre(genreId, null);
        Author author = new Author(authorId, null);
        Book book = bookService.save(new Book(0,name,author,genre));
        return "Created " + book.toString();
    }

    @ShellMethod(key = "update-book", value = "Update existed book")
    public String updateBook(@ShellOption({"--id"}) long id, @ShellOption({"--name"}) String name,
                             @ShellOption({"--author-id"}) long authorId, @ShellOption({"--genre-id"}) long genreId) {
        return bookService.find(id)
                .map(b -> new Book(id, name, new Author(authorId, null),new Genre(genreId, null)))
                .map(bookService::save)
                .map(b -> "Updated " + b)
                .orElse(String.format("Book with id=%d not found", id));
    }

    @ShellMethod(key = "remove-book", value = "Remove existed book")
    public String removeBook(@ShellOption({"--id"}) long id) {
        return bookService.find(id)
                .map(b -> {
                    bookService.delete(b);
                    return b;
                })
                .map(b -> "Removed " + b)
                .orElse(String.format("Book with id=%d not found", id));
    }

    @ShellMethod(key = "add-comment", value = "Add comment to book")
    public String addComment(@ShellOption({"--book-id"}) long bookId, @ShellOption({"--comment"}) String comment) {
        return bookService.find(bookId)
                .map(b -> bookService.addComment(b, comment))
                .map(c -> String.format("Added comment %s for book %s",c.getValue(), c.getBook()))
                .orElse(String.format("Book with id=%d not found", bookId));
    }
}
