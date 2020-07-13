package ru.otus.hw07.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.hw07.dto.BookDto;
import ru.otus.hw07.model.Author;
import ru.otus.hw07.model.Book;
import ru.otus.hw07.model.Genre;
import ru.otus.hw07.service.AuthorService;
import ru.otus.hw07.service.BookService;
import ru.otus.hw07.service.GenreService;

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
        return bookService.getAll().stream().map(this::getBookInfo).collect(Collectors.joining("\n"));
    }

    @ShellMethod(key = "create-book", value = "Create new book")
    public String createBook(@ShellOption({"--name"}) String name,
                             @ShellOption({"--author-id"}) long authorId, @ShellOption({"--genre-id"}) long genreId) {
        BookDto book = bookService.save(new Book(name, new Author(authorId, null),
                new Genre(genreId, null)));
        return "Created " + getBookInfo(book);
    }

    @ShellMethod(key = "update-book", value = "Update existed book")
    public String updateBook(@ShellOption({"--id"}) long id, @ShellOption({"--name"}) String name,
                             @ShellOption({"--author-id"}) long authorId, @ShellOption({"--genre-id"}) long genreId) {
        return bookService.find(id)
                .map(b -> {
                    Book book = b.toBook();
                    book.setName(name);
                    book.setAuthor(new Author(authorId, null));
                    book.setGenre(new Genre(genreId, null));
                    return book;
                })
                .map(bookService::save)
                .map(b -> "Updated " + getBookInfo(b))
                .orElse(String.format("Book with id=%d not found", id));
    }

    @ShellMethod(key = "remove-book", value = "Remove existed book")
    public String removeBook(@ShellOption({"--id"}) long id) {
        return bookService.find(id)
                .map(b -> {
                    bookService.delete(b.toBook());
                    return b;
                })
                .map(b -> "Removed " + getBookInfo(b))
                .orElse(String.format("Book with id=%d not found", id));
    }

    @ShellMethod(key = "add-comment", value = "Add comment to book")
    public String addComment(@ShellOption({"--book-id"}) long bookId, @ShellOption({"--comment"}) String comment) {
        return bookService.find(bookId)
                .map(b -> bookService.addComment(b.toBook(), comment))
                .map(c -> String.format("Added comment '%s' for book '%s'",c.getCommentValue(), c.getBookTitle()))
                .orElse(String.format("Book with id=%d not found", bookId));
    }

    private String getBookInfo(BookDto bookDto) {
        return String.format("Book (id=%d) '%s' by %s of %s", bookDto.getId(), bookDto.getTitle(),
                bookDto.getAuthorName(), bookDto.getGenreName());
    }
}
