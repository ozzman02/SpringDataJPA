package com.ossant;


import com.ossant.domain.AuthorUuid;
import com.ossant.domain.BookNatural;
import com.ossant.domain.BookUuid;
import com.ossant.domain.composite.AuthorComposite;
import com.ossant.domain.composite.AuthorEmbedded;
import com.ossant.domain.composite.NameId;
import com.ossant.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Created by jt on 7/4/21.
 */
@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"com.ossant.bootstrap"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MySQLIntegrationTest {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookUuidRepository bookUuidRepository;

    @Autowired
    AuthorUuidRepository authorUuidRepository;

    @Autowired
    BookNaturalRepository bookNaturalRepository;

    @Autowired
    AuthorCompositeRepository authorCompositeRepository;

    @Autowired
    AuthorEmbeddedRepository authorEmbeddedRepository;

    @Test
    void testMySQL() {
        long countBefore = bookRepository.count();
        assertThat(countBefore).isEqualTo(2);
    }

    @Test
    void bookNaturalTest() {
        BookNatural bookNatural = new BookNatural();
        bookNatural.setTitle("My Book");
        BookNatural saved = bookNaturalRepository.save(bookNatural);

        BookNatural fetched = bookNaturalRepository.getById(saved.getTitle());
        assertThat(fetched).isNotNull();
    }

    @Test
    void testBookUuid() {
        BookUuid bookUuid = bookUuidRepository.save(new BookUuid());
        assertThat(bookUuid).isNotNull();
        assertThat(bookUuid.getId());

        BookUuid fetched = bookUuidRepository.getById(bookUuid.getId());
        assertThat(fetched).isNotNull();
    }

    @Test
    void testAuthorUuid() {
        AuthorUuid authorUuid = authorUuidRepository.save(new AuthorUuid());
        assertThat(authorUuid).isNotNull();
        assertThat(authorUuid.getId()).isNotNull();

        AuthorUuid fetched = authorUuidRepository.getById(authorUuid.getId());
        assertThat(fetched).isNotNull();
    }

    @Test
    void authorCompositeTest() {
        NameId nameId = new NameId("Grace", "Patricia");
        AuthorComposite authorComposite =
                new AuthorComposite(nameId.getFirstName(), nameId.getLastName(), "CR");
        AuthorComposite saved = authorCompositeRepository.save(authorComposite);
        assertThat(saved).isNotNull();
        AuthorComposite fetched = authorCompositeRepository.findById(nameId).orElse(null);
        assertThat(fetched).isNotNull();
    }

    @Test
    void authorEmbeddedTest() {
        NameId nameId = new NameId("Grace", "Patricia");
        AuthorEmbedded authorEmbedded = new AuthorEmbedded(nameId, "CR");
        AuthorEmbedded saved = authorEmbeddedRepository.save(authorEmbedded);
        assertThat(saved).isNotNull();
        AuthorEmbedded fetched = authorEmbeddedRepository.findById(nameId).orElse(null);
        assertThat(fetched).isNotNull();
    }

}


