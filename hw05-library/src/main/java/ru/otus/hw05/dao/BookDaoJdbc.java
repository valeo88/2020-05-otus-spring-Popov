package ru.otus.hw05.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.hw05.model.Author;
import ru.otus.hw05.model.Book;
import ru.otus.hw05.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public BookDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public int count() {
        return namedParameterJdbcOperations.queryForObject("select count(*) from book", Map.of(), Integer.class);
    }

    @Override
    public long insert(Book book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcOperations.update(
                "insert into book (name,author_id,genre_id) values (:name,:author_id,:genre_id)",
        new MapSqlParameterSource(Map.of("name", book.getName(), "author_id", book.getAuthor().getId(),
                "genre_id", book.getGenre().getId())), keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public void update(Book book) {
        namedParameterJdbcOperations.update(
                "update book set name=:name, author_id=:author_id, genre_id=:genre_id where id=:id",
                Map.of("id", book.getId(), "name", book.getName(), "author_id", book.getAuthor().getId(),
                        "genre_id", book.getGenre().getId()));
    }

    @Override
    public Optional<Book> getById(long id) {
        try {
            String query = "select b.*, a.full_name as author_full_name, g.name as genre_name " +
                    "from book b inner join author a on a.id = b.author_id " +
                    "inner join genre g on g.id = b.genre_id " +
                    "where b.id = :id";
            return Optional.of(namedParameterJdbcOperations.queryForObject(query, Map.of("id", id), new BookMapper()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Book> getAll() {
        String query = "select b.*, a.full_name as author_full_name, g.name as genre_name " +
                "from book b inner join author a on a.id = b.author_id " +
                "inner join genre g on g.id = b.genre_id ";
        return namedParameterJdbcOperations.query(query, Map.of(), new BookMapper());
    }

    @Override
    public void deleteById(long id) {
        namedParameterJdbcOperations.update("delete from book where id = :id", Map.of("id", id));
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            Author author = new Author(resultSet.getLong("author_id"),
                    resultSet.getString("author_full_name"));
            Genre genre = new Genre(resultSet.getLong("genre_id"), resultSet.getString("genre_name"));
            return new Book(id, name, author, genre);
        }
    }
}
