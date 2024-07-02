package com.ossant.dao;

import com.ossant.domain.Book;

public interface BookDao {

    Book getBookById(Long id);

    Book saveBook(Book book);

    Book updateBook(Book book);

    void deleteBookById(Long id);

    Book findBookByTitle(String title);

}
