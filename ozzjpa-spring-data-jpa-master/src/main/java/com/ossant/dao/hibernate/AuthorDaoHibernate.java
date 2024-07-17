package com.ossant.dao.hibernate;

import com.ossant.dao.AuthorDao;
import com.ossant.domain.Author;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Objects;

public class AuthorDaoHibernate implements AuthorDao {

    private final EntityManagerFactory entityManagerFactory;

    public AuthorDaoHibernate(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Author getById(Long id) {
        EntityManager entityManager = getEntityManager();
        Author author = entityManager.find(Author.class, id);
        entityManager.close();
        return author;
    }

    @Override
    public Author findAuthorByName(String firstName, String lastName) {
        EntityManager entityManager = getEntityManager();
        try {
            TypedQuery<Author> query = entityManager.createQuery(GET_AUTHOR_BY_NAME_QUERY, Author.class);
            query.setParameter("first_name", firstName);
            query.setParameter("last_name", lastName);
            return query.getSingleResult();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Author saveNewAuthor(Author author) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(author);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
        return author;
    }

    @Override
    public Author updateAuthor(Author author) {
        EntityManager entityManager = getEntityManager();
        entityManager.joinTransaction();
        entityManager.merge(author);
        entityManager.flush();
        entityManager.clear();
        entityManager.close();
        return author;
    }

    @Override
    public void deleteAuthorById(Long id) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.find(Author.class, id));
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public List<Author> findAllByLastNameSortByFirstName(String lastName, Pageable pageable) {
        EntityManager entityManager = getEntityManager();
        String HQL = GET_AUTHORS_BY_LASTNAME_LIKE_QUERY;
        if (pageable.getSort().getOrderFor("firstName") != null) {
            HQL = HQL + " ORDER BY a.firstName " + Objects.requireNonNull(pageable.getSort()
                    .getOrderFor("firstName")).getDirection().name();
        }
        try {
            TypedQuery<Author> query = entityManager.createQuery(HQL, Author.class);
            query.setParameter("last_name", lastName);
            query.setFirstResult(Math.toIntExact(pageable.getOffset()));
            query.setMaxResults(pageable.getPageSize());
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }

    private EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
}
