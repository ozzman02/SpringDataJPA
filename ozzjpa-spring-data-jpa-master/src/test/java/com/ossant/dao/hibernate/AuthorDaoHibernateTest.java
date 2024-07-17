package com.ossant.dao.hibernate;

import com.ossant.dao.AuthorDao;
import com.ossant.domain.Author;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthorDaoHibernateTest {

    @Autowired
    EntityManagerFactory entityManagerFactory;

    AuthorDao authorDao;

    @BeforeEach
    void setUp() { authorDao = new AuthorDaoHibernate(entityManagerFactory); }

    @Test
    void getAuthorByIdTest() {
        Author author = authorDao.getById(1L);
        assertThat(author).isNotNull();
    }

    @Test
    void getAuthorByNameTest() {
        Author author = authorDao.findAuthorByName("Craig","Walls");
        assertThat(author).isNotNull();
    }

    @Test
    void saveAuthorTest(){
        Author author = new Author("Oscar", "Santamaria");
        Author saved = authorDao.saveNewAuthor(author);
        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
    }

    @Test
    void updateAuthorTest() {
        Author author = new Author("Grace", "Venegas");
        Author saved = authorDao.saveNewAuthor(author);
        saved.setFirstName("Patricia");
        Author updated = authorDao.updateAuthor(saved);
        assertThat(updated.getFirstName()).isEqualTo("Patricia");
    }

    @Test
    void deleteAuthorTest() {
        Author author = new Author("Sergio", "Santamaria");
        Author saved = authorDao.saveNewAuthor(author);
        authorDao.deleteAuthorById(saved.getId());
        assertThat(authorDao.getById(saved.getId())).isNull();
    }

    @Test
    void findAllByLastNameSortByFirstNameDescTest() {
        List<Author> authors = authorDao.findAllByLastNameSortByFirstName("Smith",
                PageRequest.of(0, 10, Sort.by(Sort.Order.desc("firstName"))));
        assertThat(authors).isNotNull();
        assertThat(authors.size()).isEqualTo(10);
        assertThat(authors.get(0).getFirstName()).isEqualTo("Yugal");
    }

    @Test
    void findAllByLastNameSortByFirstNameAscTest() {
        List<Author> authors = authorDao.findAllByLastNameSortByFirstName("Smith",
                PageRequest.of(0, 10, Sort.by(Sort.Order.asc("firstName"))));
        assertThat(authors).isNotNull();
        assertThat(authors.size()).isEqualTo(10);
        assertThat(authors.get(0).getFirstName()).isEqualTo("Ahmed");
    }

}
