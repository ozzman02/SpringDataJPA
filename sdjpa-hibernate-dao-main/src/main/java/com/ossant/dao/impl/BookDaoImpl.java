package com.ossant.dao.impl;

import com.ossant.dao.BookDao;
import com.ossant.domain.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDaoImpl implements BookDao {

    private final EntityManagerFactory entityManagerFactory;

    public BookDaoImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Book getById(Long id) {
        EntityManager entityManager = getEntityManager();
        Book book = entityManager.find(Book.class, id);
        entityManager.close();
        return book;
    }

    @Override
    public Book findByISBN(String isbn) {
        try (EntityManager entityManager = getEntityManager()) {
            TypedQuery<Book> query = entityManager.createQuery(GET_BOOK_BY_ISBN_QUERY, Book.class);
            query.setParameter("isbn", isbn);
            return query.getSingleResult();
        }
    }

    @Override
    public Book getByTitle(String title) {
        try (EntityManager entityManager = getEntityManager()) {
            TypedQuery<Book> query =
                    entityManager.createNamedQuery(FIND_BOOK_BY_TITLE_QUERY_NAME, Book.class);
            query.setParameter("title", title);
            return query.getSingleResult();
        }
    }

    @Override
    public Book save(Book book) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(book);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
        return book;
    }

    @Override
    public Book update(Book book) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.joinTransaction();
        entityManager.merge(book);
        entityManager.flush();
        entityManager.clear();
        entityManager.getTransaction().commit();
        entityManager.close();
        return book;
    }

    @Override
    public void deleteById(Long id) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.find(Book.class, id));
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public List<Book> findAll() {
        try (EntityManager entityManager = getEntityManager()) {
            TypedQuery<Book> query =
                    entityManager.createNamedQuery(FIND_ALL_BOOKS_QUERY_NAME, Book.class);
            return query.getResultList();
        }
    }

    @Override
    public Book findBookByTitleCriteria(String title) {
        try (EntityManager entityManager = getEntityManager()) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

            CriteriaQuery<Book> criteriaQuery =
                    criteriaBuilder.createQuery(Book.class);

            Root<Book> root = criteriaQuery.from(Book.class);

            ParameterExpression<String> titleParam =
                    criteriaBuilder.parameter(String.class);

            Predicate titlePredicate =
                    criteriaBuilder.equal(root.get("title"), titleParam);

            criteriaQuery.select(root)
                    .where(criteriaBuilder.and(titlePredicate));

            TypedQuery<Book> query = entityManager.createQuery(criteriaQuery);
            query.setParameter(titleParam, title);

            return query.getSingleResult();
        }
    }

    @Override
    public Book findBookByTitleNative(String title) {
        try (EntityManager entityManager = getEntityManager()) {
            Query query = entityManager
                    .createNativeQuery(FIND_BOOK_BY_TITLE_NATIVE_QUERY, Book.class);
            query.setParameter("title", title);
            return (Book) query.getSingleResult();
        }
    }

    private EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

}
