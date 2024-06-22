package com.ossant.dao.extractor;

import com.ossant.dao.mapper.AuthorMapper;
import com.ossant.domain.Author;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorExtractor implements ResultSetExtractor<Author> {

    @Override
    public Author extractData(ResultSet rs) throws SQLException, DataAccessException {
        rs.next();
        return new AuthorMapper().mapRow(rs, 0);
    }

}
