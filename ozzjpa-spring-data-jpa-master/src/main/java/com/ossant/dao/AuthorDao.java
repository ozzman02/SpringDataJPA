package com.ossant.dao;


import com.ossant.domain.Author;
import org.springframework.data.domain.Pageable;

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

    Author findAuthorByName(String firstName, String lastName);

    Author saveNewAuthor(Author author);

    Author updateAuthor(Author author);

    void deleteAuthorById(Long id);

    List<Author> findAllByLastNameSortByFirstName(String lastName, Pageable pageable);

}
