package com.ossant.bootstrap;

import com.ossant.domain.Book;
import com.ossant.repositories.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

//@Profile({"local, default"})
@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    private final BookRepository bookRepository;

    @Autowired
    public DataInitializer(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        bookRepository.deleteAll();
        Book book1 = new Book("Domain Driven Design", "123", "RandomMouse", null);
        Book savedBook1 = bookRepository.save(book1);
        Book book2 = new Book("Spring In Action", "456", "ORiley", null);
        Book savedBook2 = bookRepository.save(book2);
        logger.info(savedBook1.getId().toString());
        logger.info(savedBook2.getId().toString());
        bookRepository.findAll().forEach(book -> logger.info(book.toString()));
    }

}
