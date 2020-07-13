package ru.otus.hw07.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.otus.hw07.model.Author;
import ru.otus.hw07.model.Book;
import ru.otus.hw07.model.Genre;

@Data
@Builder
@AllArgsConstructor
public class BookDto {
    private long id;
    private String title;
    private long authorId;
    private String authorName;
    private long genreId;
    private String genreName;

    public Book toBook() {
        return new Book(id, title, new Author(authorId, authorName), new Genre(genreId, genreName));
    }

    public static BookDto fromBook(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .title(book.getName())
                .authorId(book.getAuthor().getId())
                .authorName(book.getAuthor().getFullName())
                .genreId(book.getGenre().getId())
                .genreName(book.getGenre().getName())
                .build();
    }
}
