package ru.otus.hw07.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.otus.hw07.model.Book;
import ru.otus.hw07.model.Comment;

@Data
@Builder
@AllArgsConstructor
public class CommentDto {
    private long id;
    private long bookId;
    private String bookTitle;
    private String commentValue;

    public Comment toComment() {
        return new Comment(id, commentValue, new Book(bookId, bookTitle, null, null));
    }

    public static CommentDto fromComment(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .bookId(comment.getBook().getId())
                .bookTitle(comment.getBook().getName())
                .commentValue(comment.getValue())
                .build();
    }
}
