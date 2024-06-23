package com.ossant.dao;

import com.ossant.domain.Book;

public interface BookDao {

    String GET_BOOK_BY_TITLE_QUERY = "SELECT b FROM Book b WHERE b.title = :title";

    Book getById(Long id);

    Book getByTitle(String title);

    Book save(Book book);

    Book update(Book book);

    void deleteById(Long id);

}
