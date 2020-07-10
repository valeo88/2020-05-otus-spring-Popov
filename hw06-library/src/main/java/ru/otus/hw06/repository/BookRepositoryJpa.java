package ru.otus.hw06.repository;

import org.springframework.stereotype.Repository;
import ru.otus.hw06.model.Book;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class BookRepositoryJpa implements BookRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public int count() {
        TypedQuery<Long> query = em.createQuery("select count(e) from Book e", Long.class);
        return query.getSingleResult().intValue();
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            em.persist(book);
            return book;
        } else {
            Book reloaded = em.find(Book.class, book.getId());
            reloaded.setName(book.getName());
            reloaded.setAuthor(book.getAuthor());
            reloaded.setGenre(book.getGenre());
            return em.merge(reloaded);
        }
    }

    @Override
    public Optional<Book> getById(long id) {
        return Optional.ofNullable(em.find(Book.class, id,
                Map.of("javax.persistence.fetchgraph", em.getEntityGraph("book-with-author-and-genre"))));
    }

    @Override
    public List<Book> getAll() {
        EntityGraph<?> entityGraph = em.getEntityGraph("book-with-author-and-genre");

        TypedQuery<Book> query = em.createQuery("select e from Book e", Book.class);
        query.setHint("javax.persistence.fetchgraph", entityGraph);

        return query.getResultList();
    }

    @Override
    public void deleteById(long id) {
        getById(id).ifPresent(em::remove);
    }
}
