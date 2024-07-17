package com.ossant.dao.hibernate;

import com.ossant.dao.BookDao;
import com.ossant.domain.Book;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookDaoHibernateTest {

    @Autowired
    EntityManagerFactory entityManagerFactory;

    BookDao bookDao;

    @BeforeEach
    void setUp() { bookDao = new BookDaoHibernate(entityManagerFactory); }

    @Test
    void getBookByIdTest() {
        assertThat(bookDao.getBookById(1L)).isNotNull();
    }

    @Test
    void getBookByTitle() {
        assertThat(bookDao.findBookByTitle("Spring in Action, 5th Edition")).isNotNull();
    }

    @Test
    void saveBookTest() {
        Book book = new Book("Domain Driven Design Reference",
                "978-0321125300", "Addison Wesley", 2L);
        assertThat(bookDao.saveBook(book)).isNotNull();
    }

    @Test
    void updateBookTest() {
        Book book = new Book("Functional Design: Principles, Patterns, and Practices",
                "978-0321125400", "Addison Wesley", 3L);
        Book saved = bookDao.saveBook(book);
        assertThat(saved).isNotNull();
        Book existingBook = bookDao.getBookById(saved.getId());
        existingBook.setIsbn("978-0321125600");
        Book updated = bookDao.updateBook(existingBook);
        assertThat(updated.getIsbn()).isEqualTo("978-0321125600");
    }

    @Test
    void deleteBookByIdTest() {
        Book book = new Book("Test, Test, and Test",
                "978-0321125400-test", "Test Publisher", 3L);
        Book saved = bookDao.saveBook(book);
        bookDao.deleteBookById(saved.getId());
        assertThat(bookDao.getBookById(saved.getId())).isNull();
    }

    @Test
    void findAllBooksTest() {
        List<Book> books = bookDao.findAllBooks();
        assertThat(books).isNotNull();
        assertThat(books.size()).isGreaterThan(0);
    }

    @Test
    void findAllBooksPageableTest() {
        List<Book> books = bookDao.findAllBooks(PageRequest.of(0, 5));
        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(5);
    }

    @Test
    void findAllBooksSortByTitleTest() {
        List<Book> books = bookDao.findAllBooksSortByTitle(PageRequest.of(0, 5,
                Sort.by(Sort.Order.desc("title"))));
        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(5);
    }

}
