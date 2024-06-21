package com.ossant.dao;

import com.ossant.domain.Author;

public interface AuthorDao {

    String GET_AUTHOR_BY_NAME_QUERY = "SELECT * FROM author WHERE FIRST_NAME = ? AND LAST_NAME = ?";

    String GET_AUTHOR_BY_ID_QUERY = "SELECT * FROM author WHERE id = ?";

    String SAVE_AUTHOR_QUERY = "INSERT INTO author (first_name, last_name) VALUES (?, ?)";

    String UPDATE_AUTHOR_QUERY = "UPDATE author SET first_name = ?, last_name = ? WHERE id = ?";

    /* This is only for MySQL */
    String GET_LAST_INSERT_ID = "SELECT LAST_INSERT_ID()";

    String DELETE_AUTHOR_QUERY = "DELETE FROM author WHERE id = ?";

    Author getById(Long id);

    Author getByName(String firstName, String lastName);

    Author save(Author author);

    Author update(Author author);

    void deleteById(Long id);

}
