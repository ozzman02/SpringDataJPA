package com.ossant;

import com.ossant.dao.AuthorDao;
import com.ossant.dao.impl.AuthorDaoImpl;
import com.ossant.domain.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.TransientDataAccessResourceException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/*
    Unsatisfied dependency expressed through field 'authorDao':
    No qualifying bean of type 'com.ossant.dao.AuthorDao'

    This issue can be fixed either by:

        @ComponentScan(basePackages = {"com.ossant.dao.impl"})

    Or
        @Import(AuthorDaoImpl.class)
*/
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(AuthorDaoImpl.class)
public class AuthorDaoTest {

    @Autowired
    private AuthorDao authorDao;

    @Test
    void getAuthorByIdTest() {
        Author author = authorDao.getById(1L);
        assertThat(author).isNotNull();
    }

    @Test
    void getAuthorByNameTest() {
        Author author = authorDao.getByName("Craig","Walls");
        assertThat(author).isNotNull();
    }

    /* Rollback is applied automatically */
    @Test
    void saveAuthorTest(){
        Author author = new Author("Oscar", "Santamaria");
        Author saved = authorDao.save(author);
        assertThat(saved).isNotNull();
    }

    @Test
    void updateAuthorTest() {
        Author author = new Author("Grace", "Venegas");
        Author saved = authorDao.save(author);
        saved.setFirstName("Patricia");
        Author updated = authorDao.update(saved);
        assertThat(updated.getFirstName()).isEqualTo("Patricia");
    }

    @Test
    void deleteAuthorTest() {
        Author author = new Author("Sergio", "Santamaria");
        Author saved = authorDao.save(author);
        authorDao.deleteById(saved.getId());
        assertThrows(TransientDataAccessResourceException.class, () -> {
            authorDao.getById(saved.getId());
        });
    }

}
