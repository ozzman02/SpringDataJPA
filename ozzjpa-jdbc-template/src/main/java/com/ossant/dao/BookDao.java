package com.ossant.dao;

import com.ossant.domain.Book;

public interface BookDao {

    String GET_BOOK_BY_ID_QUERY = "SELECT * FROM book WHERE id = ?";

    String GET_BOOK_BY_TITLE_QUERY = "SELECT * FROM book WHERE title = ?";

    String SAVE_BOOK_QUERY = "INSERT INTO book (title, isbn, publisher, author_id) VALUES (?, ?, ?, ?)";

    String UPDATE_BOOK_QUERY = """
            UPDATE book SET title = ?, isbn = ?, publisher = ?, author_id = ? WHERE id = ?
            """;

    String DELETE_BOOK_BY_ID_QUERY = "DELETE FROM book WHERE id = ?";

    /* This is only for MySQL */
    String GET_LAST_INSERT_ID = "SELECT LAST_INSERT_ID()";

    Book getById(Long id);

    Book getByTitle(String title);

    Book save(Book book);

    Book update(Book book);

    void deleteById(Long id);

}
