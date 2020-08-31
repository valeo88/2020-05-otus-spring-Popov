package ru.otus.hw10.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.hw10.dto.BookDto;
import ru.otus.hw10.service.BookSaveException;
import ru.otus.hw10.service.BookService;

import java.util.List;

@RestController
public class BookRestController {

    private final BookService bookService;

    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/api/book")
    public List<BookDto> getAllBooks() {
        return bookService.getAll();
    }

    @GetMapping("/api/book/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable long id) {
        return bookService.find(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/api/book")
    public ResponseEntity<String> saveBook(@RequestBody BookDto data) {
        try {
            bookService.save(data.toBook());
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (BookSaveException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.TEXT_HTML)
                    .body(e.getMessage());
        }
    }
}
