package ru.otus.hw06.dao;

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
public class BookDaoJpa implements BookDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public int count() {
        TypedQuery<Integer> query = em.createQuery("select count(e) from Book e", Integer.class);
        return query.getSingleResult();
    }

    @Override
    public long insert(Book book) {
        em.persist(book);
        return book.getId();
    }

    @Override
    public void update(Book book) {
        em.merge(book);
    }

    @Override
    public Optional<Book> getById(long id) {
        return Optional.ofNullable(em.find(Book.class, id,
                Map.of("javax.persistence.fetchgraph", em.getEntityGraph("book-with-all-links"))));
    }

    @Override
    public List<Book> getAll() {
        EntityGraph<?> entityGraph = em.getEntityGraph("book-with-all-links");

        TypedQuery<Book> query = em.createQuery("select e from Book e", Book.class);
        query.setHint("javax.persistence.fetchgraph", entityGraph);

        return query.getResultList();
    }

    @Override
    public void deleteById(long id) {
        getById(id).ifPresent(em::remove);
    }
}