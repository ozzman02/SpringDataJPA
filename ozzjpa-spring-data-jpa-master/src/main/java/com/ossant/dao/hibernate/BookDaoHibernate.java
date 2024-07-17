package com.ossant.dao.hibernate;

import com.ossant.dao.BookDao;
import com.ossant.domain.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Objects;

public class BookDaoHibernate implements BookDao {

    private final EntityManagerFactory entityManagerFactory;

    public BookDaoHibernate(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Book getBookById(Long id) {
        EntityManager entityManager = getEntityManager();
        Book book = entityManager.find(Book.class, id);
        entityManager.close();
        return book;
    }

    @Override
    public Book saveBook(Book book) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(book);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
        return book;
    }

    @Override
    public Book updateBook(Book book) {
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
    public void deleteBookById(Long id) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.find(Book.class, id));
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public Book findBookByTitle(String title) {
        EntityManager entityManager = getEntityManager();
        try {
            Query query = entityManager.createNativeQuery(FIND_BOOK_BY_TITLE_NATIVE_QUERY, Book.class);
            query.setParameter("title", title);
            return (Book) query.getSingleResult();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Book> findAllBooks() {
        EntityManager entityManager = getEntityManager();
        try {
            TypedQuery<Book> query = entityManager.createQuery(GET_ALL_BOOKS, Book.class);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Book> findAllBooks(int pageSize, int offset) {
        return List.of();
    }

    @Override
    public List<Book> findAllBooks(Pageable pageable) {
        EntityManager entityManager = getEntityManager();
        try {
            TypedQuery<Book> query = entityManager.createQuery(GET_ALL_BOOKS, Book.class);
            query.setFirstResult(Math.toIntExact(pageable.getOffset()));
            query.setMaxResults(pageable.getPageSize());
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Book> findAllBooksSortByTitle(Pageable pageable) {
        EntityManager entityManager = getEntityManager();

        String HQL = GET_ALL_BOOKS + " ORDER BY b.title " +
                Objects.requireNonNull(pageable.getSort().getOrderFor("title"))
                        .getDirection().name();

        try {
            TypedQuery<Book> query = entityManager.createQuery(HQL, Book.class);
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
