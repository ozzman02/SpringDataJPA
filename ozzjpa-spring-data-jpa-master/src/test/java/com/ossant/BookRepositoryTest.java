package com.ossant;

import com.ossant.domain.Book;
import com.ossant.repositories.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Test
    void emptyResultTest() {
        assertThrows(EmptyResultDataAccessException.class, () -> {
            bookRepository.readByTitle("foobar4");
        });
    }

    @Test
    void nullParamTest() {
        assertNull(bookRepository.getByTitle(null));
    }

    @Test
    void noExceptionTest() {
        assertNull(bookRepository.getByTitle("foo"));
    }

    @Test
    void bookWithStream() {
        AtomicInteger count = new AtomicInteger();
        bookRepository.findAllByTitleNotNull().forEach(book -> {
            count.incrementAndGet();
        });
        assertThat(count.get()).isGreaterThan(5);
    }

    @Test
    void bookWithFuture() throws ExecutionException, InterruptedException {
        Future<Book> bookFuture = bookRepository.queryByTitle("Clean Code");
        Book book = bookFuture.get();
        assertNotNull(book);
    }

    @Test
    void bookWithQueryTest() {
        Book book = bookRepository.findBookByTitleWithQuery("Clean Code");
        assertThat(book).isNotNull();
    }

    @Test
    void bookWithNamedQueryParams() {
       Book book = bookRepository.findBookByTitleQueryWithNamedParams("Clean Code");
        assertThat(book).isNotNull();
    }

    @Test
    void bookWithNativeQuery() {
        Book book = bookRepository.findBookByTitleNativeQuery("Clean Code");
        assertThat(book).isNotNull();
    }

    @Test
    void bookJpaNamedQuery() {
        Book book = bookRepository.findBookByTitle("Clean Code");
        assertThat(book).isNotNull();
    }

}
