package com.ossant.dao.impl;

import com.ossant.dao.BookDao;
import com.ossant.dao.mapper.BookMapper;
import com.ossant.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;


@Component
public class BookDaoImpl implements BookDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Book getById(Long id) {
        return jdbcTemplate.queryForObject(GET_BOOK_BY_ID_QUERY, getBookRowMapper(), id);
    }

    @Override
    public Book getByTitle(String title) {
        return jdbcTemplate.queryForObject(GET_BOOK_BY_TITLE_QUERY, getBookRowMapper(), title);
    }

    @Override
    public Book save(Book book) {
        jdbcTemplate.update(SAVE_BOOK_QUERY, book.getTitle(), book.getIsbn(),
                book.getPublisher(), book.getAuthorId());
        return this.getById(jdbcTemplate.queryForObject(GET_LAST_INSERT_ID, Long.class));
    }

    @Override
    public Book update(Book book) {
        jdbcTemplate.update(UPDATE_BOOK_QUERY,
                book.getTitle(), book.getIsbn(), book.getPublisher(), book.getAuthorId(), book.getId());
        return this.getById(book.getId());
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update(DELETE_BOOK_BY_ID_QUERY, id);
    }

    private RowMapper<Book> getBookRowMapper() { return new BookMapper(); }

}
