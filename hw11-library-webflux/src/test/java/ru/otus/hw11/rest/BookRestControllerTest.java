package ru.otus.hw11.rest;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import ru.otus.hw11.model.Book;
import ru.otus.hw11.service.BookService;

import static reactor.core.publisher.Mono.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest({BookRestController.class})
@Import({BookService.class})
public class BookRestControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private BookService bookService;


    @Test
    public void shouldReturnBooksWhenGetAll() {
        when(bookService.getAll()).thenReturn(Flux.just(new Book("1", "1", null, null),
                new Book("2", "2", null, null)));

        webClient.get()
                .uri("/api/book")
                .exchange()
                .expectStatus()
                .isOk();
    }
}
