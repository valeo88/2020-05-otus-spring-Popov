package ru.otus.hw06.repository;

import org.springframework.stereotype.Repository;
import ru.otus.hw06.model.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class GenreRepositoryJpa implements GenreRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Genre> getAll() {
        TypedQuery<Genre> query = em.createQuery("select e from Genre e", Genre.class);
        return query.getResultList();
    }

}
