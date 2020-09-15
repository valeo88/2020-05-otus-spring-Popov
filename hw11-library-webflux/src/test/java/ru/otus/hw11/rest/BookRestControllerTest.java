package ru.otus.hw11.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import ru.otus.hw11.dto.BookDto;
import ru.otus.hw11.model.Author;
import ru.otus.hw11.model.Book;
import ru.otus.hw11.model.Genre;
import ru.otus.hw11.service.BookService;

import static org.mockito.Mockito.when;

@WebFluxTest({BookRestController.class})
public class BookRestControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private BookService bookService;

    @Test
    public void shouldReturnBooksWhenGetAll() {
        when(bookService.getAll()).thenReturn(Flux.just(BookDto.fromBook(new Book("1", "1",
                        new Author("1", "1"), new Genre("1", "1"))),
                BookDto.fromBook(new Book("2", "2", new Author("1", "1"), new Genre("1", "1")))));

        webClient.get()
                .uri("/api/book")
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Configuration
    @Import({BookService.class, BookRestController.class})
    static class Config {
    }
}
