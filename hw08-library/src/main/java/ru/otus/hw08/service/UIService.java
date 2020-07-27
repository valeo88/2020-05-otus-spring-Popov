package ru.otus.hw08.service;

public interface UIService {

    String listAuthors();

    String listGenres();

    String listBooks();

    String createBook(String name, String authorId, String genreId);

    String updateBook(String id, String name, String authorId, String genreId);

    String removeBook(String id);

    String addComment(String bookId, String comment);

    String getComments(String bookId);
}
