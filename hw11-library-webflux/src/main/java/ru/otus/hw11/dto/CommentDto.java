package ru.otus.hw11.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.otus.hw11.model.Book;
import ru.otus.hw11.model.Comment;

@Data
@Builder
@AllArgsConstructor
public class CommentDto {
    private String id;
    private String bookId;
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
