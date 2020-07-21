package ru.otus.hw08.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.hw08.dto.BookDto;
import ru.otus.hw08.model.Author;
import ru.otus.hw08.model.Book;
import ru.otus.hw08.model.Genre;
import ru.otus.hw08.service.AuthorService;
import ru.otus.hw08.service.BookService;
import ru.otus.hw08.service.GenreService;

import java.util.Optional;
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
        return bookService.getAll().stream().map(BookDto::toString).collect(Collectors.joining("\n"));
    }

    @ShellMethod(key = "create-book", value = "Create new book")
    public String createBook(@ShellOption({"--name"}) String name,
                             @ShellOption({"--author-id"}) String authorId, @ShellOption({"--genre-id"}) String genreId) {
        Optional<Author> author = authorService.getById(authorId);
        if (author.isEmpty()) return String.format("Author with id=%s not found", authorId);

        Optional<Genre> genre = genreService.getById(genreId);
        if (genre.isEmpty()) return String.format("Genre with id=%s not found", genreId);

        BookDto bookDto = bookService.save(new Book(name, author.get(), genre.get()));
        return "Created " + bookDto;
    }

    @ShellMethod(key = "update-book", value = "Update existed book")
    public String updateBook(@ShellOption({"--id"}) String id, @ShellOption({"--name"}) String name,
                             @ShellOption({"--author-id"}) String authorId, @ShellOption({"--genre-id"}) String genreId) {
        Optional<Author> author = authorService.getById(authorId);
        if (author.isEmpty()) return String.format("Author with id=%s not found", authorId);

        Optional<Genre> genre = genreService.getById(genreId);
        if (genre.isEmpty()) return String.format("Genre with id=%s not found", genreId);

        return bookService.find(id)
                .map(b -> {
                    Book book = b.toBook();
                    book.setName(name);
                    book.setAuthor(author.get());
                    book.setGenre(genre.get());
                    return book;
                })
                .map(bookService::save)
                .map(b -> "Updated " + b)
                .orElse(String.format("Book with id=%s not found", id));
    }

    @ShellMethod(key = "remove-book", value = "Remove existed book")
    public String removeBook(@ShellOption({"--id"}) String id) {
        return bookService.find(id)
                .map(b -> {
                    bookService.delete(b.toBook());
                    return b;
                })
                .map(b -> "Removed " + b)
                .orElse(String.format("Book with id=%s not found", id));
    }

    @ShellMethod(key = "add-comment", value = "Add comment to book")
    public String addComment(@ShellOption({"--book-id"}) String bookId, @ShellOption({"--comment"}) String comment) {
        return bookService.find(bookId)
                .map(b -> bookService.addComment(b.toBook(), comment))
                .map(c -> String.format("Added comment '%s' for book '%s'",c.getCommentValue(), c.getBookTitle()))
                .orElse(String.format("Book with id=%s not found", bookId));
    }
}
