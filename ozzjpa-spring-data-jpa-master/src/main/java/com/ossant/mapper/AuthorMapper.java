package com.ossant.mapper;

import com.ossant.domain.Author;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorMapper implements RowMapper<Author> {

    @Override
    public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
        Author author = new Author();
        author.setId(rs.getLong(1));
        author.setFirstName(rs.getString(2));
        author.setLastName(rs.getString(3));
        return author;
    }

}
