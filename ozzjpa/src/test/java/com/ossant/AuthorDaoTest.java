package com.ossant;

import com.ossant.dao.AuthorDao;
import com.ossant.dao.impl.AuthorDaoImpl;
import com.ossant.domain.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

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
    void getAuthorTest() {
        Author author = authorDao.getById(1L);
        assertThat(author).isNotNull();
    }

    @Test
    void getAuthorByFirstNameTest() {
        Author author = authorDao.getAuthorByName("Craig","Walls");
        assertThat(author).isNotNull();
    }

}
