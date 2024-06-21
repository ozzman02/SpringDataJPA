package com.ossant.dao.impl;

import com.ossant.dao.AuthorDao;
import com.ossant.domain.Author;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;

@Component
public class AuthorDaoImpl implements AuthorDao {

    private final DataSource dataSource;

    public AuthorDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Author getById(Long id) {
        try (Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(GET_AUTHOR_BY_ID_QUERY)) {
            ps.setLong(1, id);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    return getAuthor(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Author getByName(String firstName, String lastName) {
        try (Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(GET_AUTHOR_BY_NAME_QUERY)) {
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    return getAuthor(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public Author save(Author author) {
        try (Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(SAVE_AUTHOR_QUERY)) {
            ps.setString(1, author.getFirstName());
            ps.setString(2, author.getLastName());
            ps.execute();
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(GET_LAST_INSERT_ID)) {
                if (resultSet.next()) {
                    Long savedId = resultSet.getLong(1);
                    return this.getById(savedId);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

    @Override
    public Author update(Author author) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_AUTHOR_QUERY)) {
            ps.setString(1, author.getFirstName());
            ps.setString(2, author.getLastName());
            ps.setLong(3, author.getId());
            ps.execute();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return this.getById(author.getId());
    }

    @Override
    public void deleteById(Long id) {
        try (Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(DELETE_AUTHOR_QUERY)) {
            ps.setLong(1, id);
            ps.execute();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private Author getAuthor(ResultSet resultSet) throws SQLException {
        return new Author(resultSet.getLong("id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name")
        );
    }
}
