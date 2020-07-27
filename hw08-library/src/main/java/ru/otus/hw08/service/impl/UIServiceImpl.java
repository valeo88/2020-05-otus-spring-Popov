package ru.otus.hw08.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.hw08.dto.BookDto;
import ru.otus.hw08.dto.CommentDto;
import ru.otus.hw08.model.Author;
import ru.otus.hw08.model.Book;
import ru.otus.hw08.model.Genre;
import ru.otus.hw08.service.AuthorService;
import ru.otus.hw08.service.BookService;
import ru.otus.hw08.service.GenreService;
import ru.otus.hw08.service.UIService;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UIServiceImpl implements UIService {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

    public UIServiceImpl(BookService bookService, AuthorService authorService, GenreService genreService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @Override
    public String listAuthors() {
        return authorService.getAll().stream().map(Author::toString).collect(Collectors.joining("\n"));
    }

    @Override
    public String listGenres() {
        return genreService.getAll().stream().map(Genre::toString).collect(Collectors.joining("\n"));
    }

    @Override
    public String listBooks() {
        return bookService.getAll().stream().map(BookDto::toString).collect(Collectors.joining("\n"));
    }

    @Override
    public String createBook(String name, String authorId, String genreId) {
        Optional<Author> author = authorService.getById(authorId);
        if (author.isEmpty()) return String.format("Author with id=%s not found", authorId);

        Optional<Genre> genre = genreService.getById(genreId);
        if (genre.isEmpty()) return String.format("Genre with id=%s not found", genreId);

        BookDto bookDto = bookService.save(new Book(name, author.get(), genre.get()));
        return "Created " + bookDto;
    }

    @Override
    public String updateBook(String id, String name, String authorId, String genreId) {
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

    @Override
    public String removeBook(String id) {
        return bookService.find(id)
                .map(b -> {
                    bookService.delete(b.toBook());
                    return b;
                })
                .map(b -> "Removed " + b)
                .orElse(String.format("Book with id=%s not found", id));
    }

    @Override
    public String addComment(String bookId, String comment) {
        return bookService.find(bookId)
                .map(b -> bookService.addComment(b.toBook(), comment))
                .map(c -> String.format("Added comment '%s' for book '%s'",c.getCommentValue(), c.getBookTitle()))
                .orElse(String.format("Book with id=%s not found", bookId));
    }

    @Override
    public String getComments(String bookId) {
        return bookService.find(bookId)
                .map(b -> bookService.getComments(b.toBook())
                        .stream()
                        .map(CommentDto::toString)
                        .collect(Collectors.joining("\n"))                )
                .orElse(String.format("Book with id=%s not found", bookId));
    }
}
