package com.ossant.dao.impl;

import com.ossant.dao.AuthorDao;
import com.ossant.dao.BookDao;
import com.ossant.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;

@Component
public class BookDaoImpl implements BookDao {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AuthorDao authorDao;

    @Override
    public Book getById(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(GET_BOOK_BY_ID_QUERY)) {
            ps.setLong(1, id);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    return getBook(resultSet);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

    @Override
    public Book getByTitle(String title) {
        try (Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(GET_BOOK_BY_TITLE_QUERY)) {
            ps.setString(1, title);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    return getBook(resultSet);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

    @Override
    public Book save(Book book) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(SAVE_BOOK_QUERY)) {
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getIsbn());
            ps.setString(3, book.getPublisher());

            if (book.getAuthorId() != null) {
                ps.setLong(4, book.getAuthorId().getId());
            }

            ps.execute();
            try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(GET_LAST_INSERT_ID)) {
                if (resultSet.next()) {
                    return getById(resultSet.getLong(1));
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return null;
    }

    @Override
    public Book update(Book book) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_BOOK_QUERY)) {
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getIsbn());
            ps.setString(3, book.getPublisher());

            if (book.getAuthorId() != null) {
                ps.setLong(4, book.getAuthorId().getId());
            }

            ps.setLong(5, book.getId());
            ps.execute();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return getById(book.getId());
    }

    @Override
    public void deleteById(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_BOOK_BY_ID_QUERY)) {
            ps.setLong(1, id);
            ps.execute();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private Book getBook(ResultSet resultSet) throws SQLException {
        return new Book(
                resultSet.getLong("id"),
                resultSet.getString("title"),
                resultSet.getString("isbn"),
                resultSet.getString("publisher"),
                authorDao.getById(resultSet.getLong("author_id"))
        );
    }
}
