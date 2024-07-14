package com.ossant.dao;

import com.ossant.domain.Author;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class AuthorDaoJDBCTemplate implements AuthorDao {

    private final JdbcTemplate jdbcTemplate;

    public AuthorDaoJDBCTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Author getById(Long id) {
        return null;
    }

    @Override
    public Author findAuthorByName(String firstName, String lastName) {
        return null;
    }

    @Override
    public Author saveNewAuthor(Author author) {
        return null;
    }

    @Override
    public Author updateAuthor(Author author) {
        return null;
    }

    @Override
    public void deleteAuthorById(Long id) {

    }

    @Override
    public List<Author> findAllByLastNameSortByFirstName(String lastName, Pageable pageable) {

        StringBuilder sb = new StringBuilder();

        sb.append("SELECT * FROM author WHERE last_name = ? ");

        if (pageable.getSort().getOrderFor("firstName") != null) {
            sb.append("ORDER BY first_name ").append(pageable.getSort()
                    .getOrderFor("firstName").getDirection().name());
        }

        sb.append(" limit ? offset ?");

        return jdbcTemplate.query(sb.toString(), getAuthorMapper(), lastName,
                pageable.getPageSize(), pageable.getOffset());
    }

    private AuthorMapper getAuthorMapper() {
        return new AuthorMapper();
    }

}
