package com.ossant.dao.impl;

import com.ossant.dao.AuthorDao;
import com.ossant.domain.Author;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class AuthorDaoImpl implements AuthorDao {

    private final EntityManagerFactory entityManagerFactory;

    public AuthorDaoImpl(EntityManagerFactory entityManagerFactory) {
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
    public Author getByName(String firstName, String lastName) {
        try (EntityManager entityManager = getEntityManager()) {
            TypedQuery<Author> query = entityManager
                    .createNamedQuery(FIND_AUTHOR_BY_NAME_QUERY_NAME, Author.class);
            query.setParameter("first_name", firstName);
            query.setParameter("last_name", lastName);
            return query.getSingleResult();
        }
    }

    @Override
    public Author save(Author author) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(author);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
        return author;
    }

    @Override
    public Author update(Author author) {
        EntityManager entityManager = getEntityManager();
        entityManager.joinTransaction();
        entityManager.merge(author);
        entityManager.flush();
        entityManager.clear();
        entityManager.close();
        return author;
    }

    @Override
    public void deleteById(Long id) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.find(Author.class, id));
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public List<Author> listAuthorsByLastNameLike(String lastName) {
        try (EntityManager entityManager = getEntityManager()) {
            TypedQuery<Author> query = entityManager
                    .createQuery(GET_AUTHORS_BY_LASTNAME_LIKE_QUERY, Author.class);
            query.setParameter("last_name", lastName + "%");
            return query.getResultList();
        }
    }

    @Override
    public List<Author> findAll() {
        try (EntityManager entityManager = getEntityManager()) {
            TypedQuery<Author> query = entityManager
                    .createNamedQuery(FIND_ALL_AUTHORS_QUERY_NAME, Author.class);
            return query.getResultList();
        }
    }

    @Override
    public Author findAuthorByNameCriteria(String firstName, String lastName) {
        try (EntityManager entityManager = getEntityManager()) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

            CriteriaQuery<Author> criteriaQuery =
                    criteriaBuilder.createQuery(Author.class);

            Root<Author> root = criteriaQuery.from(Author.class);

            ParameterExpression<String> firstNameParam =
                    criteriaBuilder.parameter(String.class);

            ParameterExpression<String> lastNameParam =
                    criteriaBuilder.parameter(String.class);

            Predicate firstNamePredicate =
                    criteriaBuilder.equal(root.get("firstName"), firstNameParam);

            Predicate lastNamePredicate =
                    criteriaBuilder.equal(root.get("lastName"), lastNameParam);

            criteriaQuery.select(root)
                    .where(criteriaBuilder.and(firstNamePredicate, lastNamePredicate));

            TypedQuery<Author> query = entityManager.createQuery(criteriaQuery);
            query.setParameter(firstNameParam, firstName);
            query.setParameter(lastNameParam, lastName);

            return query.getSingleResult();
        }
    }

    @Override
    public Author findAuthorByNameNative(String firstName, String lastName) {
        try (EntityManager entityManager = getEntityManager()) {
            Query query = entityManager.createNativeQuery(FIND_AUTHOR_BY_NAME_NATIVE_QUERY, Author.class);
            query.setParameter(1, firstName);
            query.setParameter(2, lastName);
            return (Author) query.getSingleResult();
        }
    }

    private EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
}
