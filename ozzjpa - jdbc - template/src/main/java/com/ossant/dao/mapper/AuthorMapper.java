package com.ossant.dao.mapper;

import com.ossant.domain.Author;
import com.ossant.domain.Book;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorMapper implements RowMapper<Author> {

    @Override
    public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
        Author author = new Author(
                rs.getLong("id"),
                rs.getString("first_name"),
                rs.getString("last_name")
        );
        try {
            if (rs.getString("isbn") != null) {
                author.getBooks().add(mapBook(rs));
                while (rs.next()) {
                    author.getBooks().add(mapBook(rs));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return author;
    }

    private Book mapBook(ResultSet rs) throws SQLException {
        return new Book(
                rs.getLong(4),
                rs.getString(5),
                rs.getString(6),
                rs.getString(7),
                rs.getLong(1)
        );
    }

}
