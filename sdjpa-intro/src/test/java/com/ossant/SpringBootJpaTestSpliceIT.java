package com.ossant;


import com.ossant.domain.Book;
import com.ossant.repositories.BookRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Commit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


/*
    @Rollback(value = false)
    @Commit
    @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

    Initially, the bootstrap data is not loaded therefore, we are scanning the bootstrap package.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
@ComponentScan(basePackages = {"com.ossant.bootstrap"})
public class SpringBootJpaTestSpliceIT {

    private static final Logger logger = LoggerFactory.getLogger(SpringBootJpaTestSpliceIT.class);

    @Autowired
    private BookRepository bookRepository;

    @Commit
    @Order(1)
    @Test
    void testJpaSplice() {
        long countBefore = bookRepository.count();
        assertThat(countBefore).isEqualTo(2);
        logger.info("Count Before: {}", countBefore);
        bookRepository.save(new Book("MyBook", "123456", "Self"));
        long countAfter = bookRepository.count();
        logger.info("Count After: {}", countAfter);
        assertThat(countBefore).isLessThan(countAfter);
    }

    @Order(2)
    @Test
    void testJpaSpliceTransaction() {
        long countBefore = bookRepository.count();
        assertThat(countBefore).isEqualTo(3);
    }
}
