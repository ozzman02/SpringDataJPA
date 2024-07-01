package com.ossant.dao;

import com.ossant.domain.Book;

import java.util.List;

public interface BookDao {

    String GET_BOOK_BY_TITLE_QUERY = "SELECT b FROM Book b WHERE b.title = :title";

    String GET_BOOK_BY_ISBN_QUERY = "SELECT b FROM Book b WHERE b.isbn = :isbn";

    String FIND_ALL_BOOKS_QUERY_NAME = "find_all_books";

    String FIND_ALL_BOOKS_QUERY = "FROM Book";

    String FIND_BOOK_BY_TITLE_QUERY_NAME = "find_book_by_title";

    String FIND_BOOK_BY_TITLE_QUERY = "FROM Book b WHERE b.title = :title";

    String FIND_BOOK_BY_TITLE_NATIVE_QUERY = "SELECT * FROM book b WHERE b.title = :title";

    Book getById(Long id);

    Book findByISBN(String isbn);

    Book getByTitle(String title);

    Book save(Book book);

    Book update(Book book);

    void deleteById(Long id);

    List<Book> findAll();

    Book findBookByTitleCriteria(String title);

    Book findBookByTitleNative(String title);

}
