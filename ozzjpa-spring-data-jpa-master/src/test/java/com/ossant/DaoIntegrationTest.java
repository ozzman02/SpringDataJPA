package com.ossant;

import com.ossant.dao.AuthorDao;
import com.ossant.dao.AuthorDaoImpl;
import com.ossant.dao.BookDao;
import com.ossant.dao.BookDaoImpl;
import com.ossant.domain.Author;
import com.ossant.domain.Book;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@DataJpaTest
@Import({AuthorDaoImpl.class, BookDaoImpl.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DaoIntegrationTest {

    @Autowired
    private AuthorDao authorDao;

    @Autowired
    private BookDao bookDao;

    @Test
    void testDeleteBook() {
        Book book = new Book();
        book.setIsbn("1234");
        book.setPublisher("Self");
        book.setTitle("my book");
        Book saved = bookDao.saveBook(book);
        bookDao.deleteBookById(saved.getId());
        assertThrows(EntityNotFoundException.class, () -> {
            bookDao.getBookById(saved.getId());
        });
    }

    @Test
    void updateBookTest() {
        Book book = new Book();
        book.setIsbn("1234");
        book.setPublisher("Self");
        book.setTitle("my book");
        book.setAuthorId(3L);
        Book saved = bookDao.saveBook(book);
        saved.setTitle("New Book");
        Book updated = bookDao.updateBook(saved);
        Book fetched = bookDao.getBookById(updated.getId());
        assertThat(fetched.getTitle()).isEqualTo("New Book");
    }

    @Test
    void testSaveBook() {
        Book book = new Book();
        book.setIsbn("1234");
        book.setPublisher("Self");
        book.setTitle("my book");
        book.setAuthorId(3L);
        Book saved = bookDao.saveBook(book);
        assertThat(saved).isNotNull();
    }

    @Test
    void testGetBookByTitle() {
        Book book = bookDao.findBookByTitle("Clean Code");
        assertThat(book).isNotNull();
    }

    @Test
    void testGetBookByTitleException() {
        assertThrows(EntityNotFoundException.class, () -> {
            bookDao.findBookByTitle("foo");
        });
    }

    @Test
    void testGetBook() {
        Book book = bookDao.getBookById(3L);
        assertThat(book.getId()).isNotNull();
    }

    @Test
    void testDeleteAuthor() {
        Author author = new Author();
        author.setFirstName("john");
        author.setLastName("t");
        Author saved = authorDao.saveNewAuthor(author);
        authorDao.deleteAuthorById(saved.getId());
        assertThat(authorDao.getById(saved.getId())).isNull();
    }

    @Test
    void testUpdateAuthor() {
        Author author = new Author();
        author.setFirstName("john");
        author.setLastName("t");
        Author saved = authorDao.saveNewAuthor(author);
        saved.setLastName("Thompson");
        Author updated = authorDao.updateAuthor(saved);
        assertThat(updated.getLastName()).isEqualTo("Thompson");
    }

    @Test
    void testSaveAuthor() {
        Author author = new Author();
        author.setFirstName("John");
        author.setLastName("Thompson");
        Author saved = authorDao.saveNewAuthor(author);
        assertThat(saved).isNotNull();
    }

    @Test
    void testGetAuthorByName() {
        Author author = authorDao.findAuthorByName("Craig", "Walls");
        assertThat(author).isNotNull();
    }

    @Test
    void testGetAuthorByNameException() {
        assertThrows(EntityNotFoundException.class, () -> {
            authorDao.findAuthorByName("FOO", "BAR");
        });
    }

    @Test
    void testGetAuthor() {
        Author author = authorDao.getById(1L);
        assertThat(author).isNotNull();
    }

}
