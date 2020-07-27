package ru.otus.hw08.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.hw08.service.UIService;

@ShellComponent
public class ApplicationCommands {

    private final UIService uiService;

    public ApplicationCommands(UIService uiService) {
        this.uiService = uiService;
    }

    @ShellMethod(key = "list-authors", value = "List all available authors")
    public String listAuthors() {
        return uiService.listAuthors();
    }

    @ShellMethod(key = "list-genres", value = "List all available genres")
    public String listGenres() {
        return uiService.listGenres();
    }

    @ShellMethod(key = "list-books", value = "List all books")
    public String listBooks() {
        return uiService.listBooks();
    }

    @ShellMethod(key = "create-book", value = "Create new book")
    public String createBook(@ShellOption({"--name"}) String name,
                             @ShellOption({"--author-id"}) String authorId, @ShellOption({"--genre-id"}) String genreId) {
        return uiService.createBook(name, authorId, genreId);
    }

    @ShellMethod(key = "update-book", value = "Update existed book")
    public String updateBook(@ShellOption({"--id"}) String id, @ShellOption({"--name"}) String name,
                             @ShellOption({"--author-id"}) String authorId, @ShellOption({"--genre-id"}) String genreId) {
        return uiService.updateBook(id, name, authorId, genreId);
    }

    @ShellMethod(key = "remove-book", value = "Remove existed book")
    public String removeBook(@ShellOption({"--id"}) String id) {
        return uiService.removeBook(id);
    }

    @ShellMethod(key = "add-comment", value = "Add comment to book")
    public String addComment(@ShellOption({"--book-id"}) String bookId, @ShellOption({"--comment"}) String comment) {
        return uiService.addComment(bookId, comment);
    }

    @ShellMethod(key = "show-comments", value = "Show comments of book")
    public String showComments(@ShellOption({"--book-id"}) String bookId) {
        return uiService.getComments(bookId);
    }
}
