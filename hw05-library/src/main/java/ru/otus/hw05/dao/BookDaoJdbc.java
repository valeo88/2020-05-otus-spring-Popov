package ru.otus.hw05.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.hw05.model.Author;
import ru.otus.hw05.model.Book;
import ru.otus.hw05.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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
    public void insert(Book book) {
        namedParameterJdbcOperations.update(
                "insert into book (id,name,author_id,genre_id) values (:id,:name,:author_id,:genre_id)",
        Map.of("id", book.getId(), "name", book.getName(), "author_id", book.getAuthor().getId(),
                "genre_id", book.getGenre().getId()));
    }

    @Override
    public Book getById(long id) {
        String query = "select b.*, a.name as author_name, a.surname as author_surname, g.name as genre_name " +
                "from book b inner join author a on a.id = book.author_id " +
                "inner join genre g on g.id = book.genre_id " +
                "where b.id = :id";
        return namedParameterJdbcOperations.queryForObject(query, Map.of("id", id),
                new BookMapper());
    }

    @Override
    public List<Book> getAll() {
        String query = "select b.*, a.name as author_name, a.surname as author_surname, g.name as genre_name " +
                "from book b inner join author a on a.id = book.author_id " +
                "inner join genre g on g.id = book.genre_id ";
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
                    resultSet.getString("author_name"), resultSet.getString("author_surname"));
            Genre genre = new Genre(resultSet.getLong("genre_id"), resultSet.getString("genre_name"));
            return new Book(id, name, author, genre);
        }
    }
}
