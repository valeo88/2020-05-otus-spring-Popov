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
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/api/book")
    public Mono<ResponseEntity<Object>> saveBook(@RequestBody BookDto data) {
        return bookService.save(data.toBook())
            .flatMap(bookDto -> Mono.just(new ResponseEntity<>(HttpStatus.CREATED)))
            .doOnError(error -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.TEXT_HTML)
                .body(error.getMessage())));

    }
}
