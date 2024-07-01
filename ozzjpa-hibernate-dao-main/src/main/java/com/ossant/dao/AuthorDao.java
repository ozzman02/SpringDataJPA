package com.ossant.dao;

import com.ossant.domain.Author;

import java.util.List;


public interface AuthorDao {

    String GET_AUTHOR_BY_NAME_QUERY = """
            SELECT a FROM Author a WHERE a.firstName = :first_name AND a.lastName = :last_name
            """;
    String GET_AUTHORS_BY_LASTNAME_LIKE_QUERY = """
            SELECT a FROM Author a WHERE a.lastName like :last_name
            """;

    String FIND_ALL_AUTHORS_QUERY_NAME = "author_find_all";

    String FIND_ALL_AUTHORS_QUERY = "FROM Author";

    String FIND_AUTHOR_BY_NAME_QUERY_NAME = "find_by_name";

    String FIND_AUTHOR_BY_NAME_QUERY = """
            FROM Author a WHERE a.firstName = :first_name AND a.lastName = :last_name
            """;

    String FIND_AUTHOR_BY_NAME_NATIVE_QUERY = """
            SELECT * FROM author a WHERE a.first_name = ? AND a.last_name = ?
            """;

    Author getById(Long id);

    Author getByName(String firstName, String lastName);

    Author save(Author author);

    Author update(Author author);

    void deleteById(Long id);

    List<Author> listAuthorsByLastNameLike(String lastName);

    List<Author> findAll();

    Author findAuthorByNameCriteria(String firstName, String lastName);

    Author findAuthorByNameNative(String firstName, String lastName);

}
