package ru.otus.hw10.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.otus.hw10.dto.BookDto;
import ru.otus.hw10.service.BookService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("REST контроллер для книг должен ")
@RunWith(SpringRunner.class)
@WebMvcTest(BookRestController.class)
public class BookRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;

    @DisplayName("выдавать список книг при GET запросе без параметров")
    @Test
    public void shouldReturnListOfBookDtosOnGetBookWithoutId() throws Exception {
        List<BookDto> samples = sampleBookDtos();
        when(bookService.getAll()).thenReturn(samples);

        mvc.perform( MockMvcRequestBuilders
                .get("/api/book")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(samples.size())))
                .andExpect(jsonPath("$[*].id").isNotEmpty());
    }

    @DisplayName("выдавать статус 201 CREATED при успешном сохранении книги")
    @Test
    public void shouldReturnCreatedWhenCreateNewBook() throws Exception {
        BookDto sample = new BookDto(1, "Book1", 1,
                "Author1", 1, "Genre1");
        when(bookService.save(any())).thenReturn(sample);

        mvc.perform( MockMvcRequestBuilders
                .post("/api/book")
                .content(objectMapper.writeValueAsString(sample))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    private List<BookDto> sampleBookDtos() {
        return Arrays.asList(new BookDto(1, "Book1", 1,
                        "Author1", 1, "Genre1"),
                new BookDto(2, "Book1", 1,
                        "Author1", 2, "Genre2"));
    }
}
