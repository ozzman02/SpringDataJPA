package com.ossant.dao;

import com.ossant.domain.Author;

public interface AuthorDao {

    Author getById(Long id);

    Author getAuthorByName(String firstName, String lastName);

}
