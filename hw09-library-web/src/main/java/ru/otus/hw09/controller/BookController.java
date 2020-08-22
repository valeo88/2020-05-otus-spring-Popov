package ru.otus.hw09.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.hw09.dto.BookDto;
import ru.otus.hw09.service.AuthorService;
import ru.otus.hw09.service.BookNotFoundException;
import ru.otus.hw09.service.BookService;
import ru.otus.hw09.service.GenreService;

@Controller
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;

    public BookController(BookService bookService, AuthorService authorService, GenreService genreService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @GetMapping("/")
    public String listPage(Model model) {
        model.addAttribute("books", bookService.getAll());
        return "bookList";
    }

    @GetMapping("/create")
    public String createPage(Model model) {
        model.addAttribute("book", new BookDto());
        model.addAttribute("allAuthors", authorService.getAll());
        model.addAttribute("allGenres", genreService.getAll());
        return "bookEdit";
    }

    @GetMapping("/edit")
    public String editPage(@RequestParam("id") long id, Model model) {
        BookDto bookDto = bookService.find(id).orElseThrow(BookNotFoundException::new);
        model.addAttribute("book", bookDto);
        model.addAttribute("allAuthors", authorService.getAll());
        model.addAttribute("allGenres", genreService.getAll());
        return "bookEdit";
    }

    @PostMapping("/edit")
    public String saveBook(BookDto bookDto, Model model) {
        BookDto saved = bookService.save(bookDto.toBook());
        model.addAttribute("book", saved);
        model.addAttribute("allAuthors", authorService.getAll());
        model.addAttribute("allGenres", genreService.getAll());
        return "bookEdit";
    }

}
