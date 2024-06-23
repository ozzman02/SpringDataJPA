package com.ossant.dao;

import com.ossant.domain.Author;


public interface AuthorDao {

    String GET_AUTHOR_BY_NAME_QUERY = """
            SELECT a FROM Author a WHERE a.firstName = :first_name AND a.lastName = :last_name
            """;

    Author getById(Long id);

    Author getByName(String firstName, String lastName);

    Author save(Author author);

    Author update(Author author);

    void deleteById(Long id);

}
