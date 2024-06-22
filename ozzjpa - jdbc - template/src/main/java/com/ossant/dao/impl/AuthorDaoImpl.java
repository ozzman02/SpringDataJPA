package com.ossant.dao.impl;

import com.ossant.dao.AuthorDao;
import com.ossant.dao.extractor.AuthorExtractor;
import com.ossant.dao.mapper.AuthorMapper;
import com.ossant.domain.Author;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class AuthorDaoImpl implements AuthorDao {

    private final JdbcTemplate jdbcTemplate;

    public AuthorDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Author getById(Long id) {
        //return jdbcTemplate.queryForObject(GET_AUTHOR_BY_ID_QUERY, getAuthorRowMapper(), id);
        return jdbcTemplate.query(ENHANCED_GET_AUTHOR_BY_ID_QUERY, new AuthorExtractor(), id);
    }

    @Override
    public Author getByName(String firstName, String lastName) {
        return jdbcTemplate.queryForObject(GET_AUTHOR_BY_NAME_QUERY, getAuthorRowMapper(), firstName, lastName);
    }

    @Override
    public Author save(Author author) {
        jdbcTemplate.update(SAVE_AUTHOR_QUERY, author.getFirstName(), author.getLastName());
        Long createdId = jdbcTemplate.queryForObject(GET_LAST_INSERT_ID, Long.class);
        return this.getById(createdId);
    }

    @Override
    public Author update(Author author) {
        jdbcTemplate.update(UPDATE_AUTHOR_QUERY,
                author.getFirstName(), author.getLastName(), author.getId());
        return this.getById(author.getId());
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update(DELETE_AUTHOR_QUERY, id);
    }

    private RowMapper<Author> getAuthorRowMapper() {return new AuthorMapper(); }
}
