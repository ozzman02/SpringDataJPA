package com.ossant.domain;

import jakarta.persistence.*;

import static com.ossant.dao.AuthorDao.*;

@NamedQueries({
        @NamedQuery(name = FIND_ALL_AUTHORS_QUERY_NAME, query = FIND_ALL_AUTHORS_QUERY),
        @NamedQuery(name = FIND_AUTHOR_BY_NAME_QUERY_NAME, query = FIND_AUTHOR_BY_NAME_QUERY)
})
@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    public Author() {
    }

    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
