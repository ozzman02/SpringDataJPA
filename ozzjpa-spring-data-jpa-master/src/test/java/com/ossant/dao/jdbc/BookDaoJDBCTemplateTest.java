package com.ossant.dao.jdbc;

import com.ossant.dao.BookDao;
import com.ossant.domain.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = {"com.ossant.dao.jdbc"})
public class BookDaoJDBCTemplateTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    BookDao bookDao;

    @BeforeEach
    void setUp() {
        bookDao = new BookDaoJDBCTemplate(jdbcTemplate);
    }

    @Test
    void getById() {
        Book book = bookDao.getBookById(3L);
        assertThat(book.getId()).isNotNull();
    }

    @Test
    void findBookByTitle() {
        Book book = bookDao.findBookByTitle("Clean Code");
        assertThat(book).isNotNull();
    }

    @Test
    void saveNewBook() {
        Book book = new Book();
        book.setIsbn("1234");
        book.setPublisher("Self");
        book.setTitle("my book");
        book.setAuthorId(1L);

        Book saved = bookDao.saveBook(book);

        assertThat(saved).isNotNull();
    }

    @Test
    void updateBook() {
        Book book = new Book();
        book.setIsbn("1234");
        book.setPublisher("Self");
        book.setTitle("My book");
        book.setAuthorId(1L);
        Book saved = bookDao.saveBook(book);
        saved.setTitle("New Book");
        bookDao.updateBook(saved);
        Book fetched = bookDao.getBookById(saved.getId());
        assertThat(fetched.getTitle()).isEqualTo("New Book");
    }

    @Test
    void deleteBookById() {

        Book book = new Book();
        book.setIsbn("1234");
        book.setPublisher("Self");
        book.setTitle("my book");
        Book saved = bookDao.saveBook(book);

        bookDao.deleteBookById(saved.getId());

        assertThrows(EmptyResultDataAccessException.class, () -> {
            bookDao.getBookById(saved.getId());
        });
    }

    @Test
    void findAllBooksTest() {
        List<Book> books = bookDao.findAllBooks();
        assertThat(books).isNotNull();
        assertThat(books.size()).isGreaterThanOrEqualTo(5);
    }

    @Test
    void findAllBooksPage1() {
        List<Book> books = bookDao.findAllBooks(1, 0);
        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(1);
    }

    @Test
    void findAllBooksPage2() {
        List<Book> books = bookDao.findAllBooks(2, 1);
        assertThat(books).isNotNull();
        assertThat(books.size()).isEqualTo(2);
    }

    @Test
    void findAllBooksPage1Pageable() {
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
