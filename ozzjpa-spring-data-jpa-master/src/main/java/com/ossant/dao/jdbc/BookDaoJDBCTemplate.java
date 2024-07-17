package com.ossant.dao.jdbc;

import com.ossant.dao.BookDao;
import com.ossant.mapper.BookMapper;
import com.ossant.domain.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class BookDaoJDBCTemplate implements BookDao {

    private final JdbcTemplate jdbcTemplate;

    public BookDaoJDBCTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Book getBookById(Long id) {
        return jdbcTemplate.queryForObject(GET_BOOK_BY_ID_QUERY, getBookMapper(), id);
    }

    @Override
    public Book saveBook(Book book) {
        jdbcTemplate.update(SAVE_BOOK_QUERY,
                book.getIsbn(), book.getPublisher(), book.getTitle(), book.getAuthorId());

        Long createdId = jdbcTemplate.queryForObject(GET_LAST_INSERT_ID, Long.class);

        return this.getBookById(createdId);
    }

    @Transactional
    @Override
    public Book updateBook(Book book) {
        jdbcTemplate.update(UPDATE_BOOK_QUERY, book.getTitle(), book.getIsbn(),
                book.getPublisher(), book.getAuthorId(), book.getId());

        Book bookById = getBookById(book.getId());

        return this.getBookById(book.getId());
    }

    @Override
    public void deleteBookById(Long id) {
        jdbcTemplate.update(DELETE_BOOK_BY_ID_QUERY, id);
    }

    @Override
    public Book findBookByTitle(String title) {
        return jdbcTemplate.queryForObject(GET_BOOK_BY_TITLE_QUERY, getBookMapper(), title);

    }

    @Override
    public List<Book> findAllBooks() {
        return jdbcTemplate.query(FIND_ALL_BOOKS_QUERY, getBookMapper());
    }

    @Override
    public List<Book> findAllBooks(int pageSize, int offset) {
        return jdbcTemplate.query(FIND_ALL_BOOKS_PAGINATED_QUERY, getBookMapper(), pageSize, offset);
    }

    @Override
    public List<Book> findAllBooks(Pageable pageable) {
        return jdbcTemplate.query(FIND_ALL_BOOKS_PAGINATED_QUERY, getBookMapper(),
                pageable.getPageSize(), pageable.getOffset());
    }

    @Override
    public List<Book> findAllBooksSortByTitle(Pageable pageable) {
        String sql = "SELECT * FROM book ORDER BY title " +
                pageable.getSort().getOrderFor("title").getDirection().name()
                + " limit ? offset ?";
        return jdbcTemplate.query(sql, getBookMapper(), pageable.getPageSize(), pageable.getOffset());
    }

    private BookMapper getBookMapper() {
        return new BookMapper();
    }
}
