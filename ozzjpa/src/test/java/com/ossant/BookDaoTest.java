package com.ossant;

import com.ossant.dao.AuthorDao;
import com.ossant.dao.BookDao;
import com.ossant.dao.impl.AuthorDaoImpl;
import com.ossant.dao.impl.BookDaoImpl;
import com.ossant.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({BookDaoImpl.class, AuthorDaoImpl.class})
public class BookDaoTest {

    @Autowired
    private BookDao bookDao;

    @Autowired
    private AuthorDao authorDao;

    @Test
    void getBookByIdTest() {
        assertThat(bookDao.getById(1L)).isNotNull();
    }

    @Test
    void getBookByTitle() {
        assertThat(bookDao.getByTitle("Spring in Action, 5th Edition")).isNotNull();
    }

    @Test
    void saveBookTest() {

        Book book = new Book("Domain Driven Design Reference",
                "978-0321125300", "Addison Wesley", authorDao.getById(2L));
        assertThat(bookDao.save(book)).isNotNull();
    }

    @Test
    void updateBookTest() {
        Book book = new Book("Functional Design: Principles, Patterns, and Practices",
                "978-0321125400", "Addison Wesley", authorDao.getById(3L));
        Book saved = bookDao.save(book);
        assertThat(saved).isNotNull();
        Book existingBook = bookDao.getById(saved.getId());
        existingBook.setIsbn("978-0321125600");
        Book updated = bookDao.update(existingBook);
        assertThat(updated.getIsbn()).isEqualTo("978-0321125600");
    }

    @Test
    void deleteBookByIdTest() {
        Book book = new Book("Test, Test, and Test",
                "978-0321125400-test", "Test Publisher", authorDao.getById(3L));
        Book saved = bookDao.save(book);
        bookDao.deleteById(saved.getId());
        assertThat(bookDao.getById(saved.getId())).isNull();
    }

}
