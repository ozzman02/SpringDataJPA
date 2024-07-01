package com.ossant;

import com.ossant.dao.AuthorDao;
import com.ossant.dao.BookDao;
import com.ossant.dao.impl.AuthorDaoImpl;
import com.ossant.dao.impl.BookDaoImpl;
import com.ossant.domain.Author;
import com.ossant.domain.Book;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({AuthorDaoImpl.class, BookDaoImpl.class})
public class DaoIntegrationTest {

    @Autowired
    private AuthorDao authorDao;

    @Autowired
    private BookDao bookDao;

    @Order(1)
    @Test
    void getAuthorByIdTest() {
        Author author = authorDao.getById(1L);
        assertThat(author).isNotNull();
    }

    @Order(2)
    @Test
    void getAuthorByNameTest() {
        Author author = authorDao.getByName("Craig","Walls");
        assertThat(author).isNotNull();
    }

    @Order(3)
    @Test
    void saveAuthorTest(){
        Author author = new Author("Oscar", "Santamaria");
        Author saved = authorDao.save(author);
        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
    }

    @Order(4)
    @Test
    void updateAuthorTest() {
        Author author = new Author("Grace", "Venegas");
        Author saved = authorDao.save(author);
        saved.setFirstName("Patricia");
        Author updated = authorDao.update(saved);
        assertThat(updated.getFirstName()).isEqualTo("Patricia");
    }

    @Order(5)
    @Test
    void deleteAuthorTest() {
        Author author = new Author("Sergio", "Santamaria");
        Author saved = authorDao.save(author);
        authorDao.deleteById(saved.getId());
        assertThat(authorDao.getById(saved.getId())).isNull();
    }

    @Order(6)
    @Test
    void testListAuthorsByLastNameLike() {
        List<Author> authors = authorDao.listAuthorsByLastNameLike("Wall");
        assertThat(authors).isNotNull();
        assertThat(authors.size()).isGreaterThan(0);
    }

    @Order(7)
    @Test
    void findAllAuthorsTest() {
        List<Author> authors = authorDao.findAll();
        assertThat(authors).isNotNull();
        assertThat(authors.size()).isGreaterThan(0);
    }

    @Order(8)
    @Test
    void getAuthorByNameCriteria() {
        Author author = authorDao.findAuthorByNameCriteria("Craig", "Walls");
        assertThat(author).isNotNull();
    }

    @Order(9)
    @Test
    void getAuthorByNameNative() {
        Author author = authorDao.findAuthorByNameNative("Craig", "Walls");
        assertThat(author).isNotNull();
    }

    @Order(10)
    @Test
    void getBookByIdTest() {
        assertThat(bookDao.getById(1L)).isNotNull();
    }

    @Order(11)
    @Test
    void getBookByTitle() {
        assertThat(bookDao.getByTitle("Spring in Action, 5th Edition")).isNotNull();
    }

    @Order(12)
    @Test
    void saveBookTest() {
        Book book = new Book("Domain Driven Design Reference",
                "978-0321125300", "Addison Wesley", 2L);
        assertThat(bookDao.save(book)).isNotNull();
    }

    @Order(13)
    @Test
    void updateBookTest() {
        Book book = new Book("Functional Design: Principles, Patterns, and Practices",
                "978-0321125400", "Addison Wesley", 3L);
        Book saved = bookDao.save(book);
        assertThat(saved).isNotNull();
        Book existingBook = bookDao.getById(saved.getId());
        existingBook.setIsbn("978-0321125600");
        Book updated = bookDao.update(existingBook);
        assertThat(updated.getIsbn()).isEqualTo("978-0321125600");
    }

    @Order(14)
    @Test
    void deleteBookByIdTest() {
        Book book = new Book("Test, Test, and Test",
                "978-0321125400-test", "Test Publisher", 3L);
        Book saved = bookDao.save(book);
        bookDao.deleteById(saved.getId());
        assertThat(bookDao.getById(saved.getId())).isNull();
    }

    @Order(15)
    @Test
    void findBookByISBNTest() {
        Book book = new Book();
        book.setIsbn("12345" + RandomString.make());
        book.setTitle("isbn test");
        Book saved = bookDao.save(book);
        Book fetched = bookDao.findByISBN(book.getIsbn());
        assertThat(fetched).isNotNull();
    }

    @Order(16)
    @Test
    void findAllBooksTest() {
        List<Book> books = bookDao.findAll();
        assertThat(books).isNotNull();
        assertThat(books.size()).isGreaterThan(0);
    }

    @Order(17)
    @Test
    void findBookByTitleCriteria() {
        assertThat(bookDao.findBookByTitleCriteria("Spring in Action, 5th Edition"))
                .isNotNull();
    }

    @Order(18)
    @Test
    void findBookByTitleNative() {
        assertThat(bookDao.findBookByTitleNative("Spring in Action, 5th Edition"))
                .isNotNull();
    }

}