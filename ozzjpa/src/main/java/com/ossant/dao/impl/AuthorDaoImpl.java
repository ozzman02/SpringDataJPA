package com.ossant.dao.impl;

import com.ossant.dao.AuthorDao;
import com.ossant.domain.Author;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;

@Component
public class AuthorDaoImpl implements AuthorDao {

    private static final String GET_AUTHOR_BY_NAME_QUERY = """
                SELECT * FROM author WHERE FIRST_NAME = ? AND LAST_NAME = ?
                """;

    private static final String GET_AUTHOR_BY_ID_QUERY = "SELECT * FROM author where id = ?";

    private final DataSource dataSource;

    public AuthorDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Author getById(Long id) {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(GET_AUTHOR_BY_ID_QUERY);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Author author = new Author();
                author.setId(id);
                author.setFirstName(resultSet.getString("first_name"));
                author.setFirstName(resultSet.getString("last_name"));
                return author;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Author getAuthorByName(String firstName, String lastName) {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(GET_AUTHOR_BY_NAME_QUERY);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Author author = new Author();
                author.setId(resultSet.getLong("id"));
                author.setFirstName(resultSet.getString("first_name"));
                author.setFirstName(resultSet.getString("last_name"));
                return author;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
