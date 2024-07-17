package com.ossant.dao;

import com.ossant.domain.Book;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookDao {

    String FIND_ALL_BOOKS_QUERY = "SELECT * FROM book";

    String GET_ALL_BOOKS = "SELECT b FROM Book b";

    String FIND_ALL_BOOKS_PAGINATED_QUERY = "SELECT * FROM book limit ? offset ?";

    String GET_BOOK_BY_ID_QUERY = "SELECT * FROM book WHERE id = ?";

    String GET_BOOK_BY_TITLE_QUERY = "SELECT * FROM book WHERE title = ?";

    String FIND_BOOK_BY_TITLE_NATIVE_QUERY = "SELECT * FROM book b WHERE b.title = :title";

    String SAVE_BOOK_QUERY = "INSERT INTO book (title, isbn, publisher, author_id) VALUES (?, ?, ?, ?)";

    String UPDATE_BOOK_QUERY = """
            UPDATE book SET title = ?, isbn = ?, publisher = ?, author_id = ? WHERE id = ?
            """;

    String DELETE_BOOK_BY_ID_QUERY = "DELETE FROM book WHERE id = ?";

    /* This is only for MySQL */
    String GET_LAST_INSERT_ID = "SELECT LAST_INSERT_ID()";

    Book getBookById(Long id);

    Book saveBook(Book book);

    Book updateBook(Book book);

    void deleteBookById(Long id);

    Book findBookByTitle(String title);

    List<Book> findAllBooks();

    List<Book> findAllBooks(int pageSize, int offset);

    List<Book> findAllBooks(Pageable pageable);

    List<Book> findAllBooksSortByTitle(Pageable pageable);

}
