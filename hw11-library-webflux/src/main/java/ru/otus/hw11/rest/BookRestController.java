package ru.otus.hw11.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw11.dto.BookDto;
import ru.otus.hw11.service.BookService;

@RestController
public class BookRestController {

    private final BookService bookService;

    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/api/book")
    public Flux<BookDto> getAllBooks() {
        return bookService.getAll();
    }

    @GetMapping("/api/book/{id}")
    public Mono<ResponseEntity<BookDto>> getBookById(@PathVariable String id) {
        return bookService.find(id)
                .flatMap(bookDto -> Mono.fromSupplier(() -> ResponseEntity.ok(bookDto)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/api/book")
    public Mono<ResponseEntity<BookDto>> saveBook(@RequestBody BookDto data) {
        return bookService.save(data.toBook())
            .flatMap(bookDto -> Mono.fromSupplier(() -> ResponseEntity.status(HttpStatus.CREATED).body(bookDto)))
            .doOnError(error -> Mono.fromSupplier(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.TEXT_HTML)
                .body(error.getMessage())));

    }
}
