package com.ossant.dao.jpa;

import com.ossant.dao.BookDao;
import com.ossant.domain.Book;
import com.ossant.repositories.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookDaoImpl implements BookDao {

    private final BookRepository bookRepository;

    public BookDaoImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Transactional
    @Override
    public Book updateBook(Book book) {
        return bookRepository.findById(book.getId()).map(existingBook -> {
            existingBook.setTitle(book.getTitle());
            existingBook.setIsbn(book.getIsbn());
            existingBook.setAuthorId(book.getAuthorId());
            existingBook.setPublisher(book.getPublisher());
            return existingBook;
        }).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Book findBookByTitle(String title) {
        return bookRepository.findByTitle(title).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> findAllBooks(int pageSize, int offset) {
        Pageable pageable = PageRequest.ofSize(pageSize);
        if (offset > 0) {
            pageable = pageable.withPage(offset / pageSize);
        } else {
            pageable = pageable.withPage(0);
        }
        return this.findAllBooks(pageable);
    }

    @Override
    public List<Book> findAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable).getContent();
    }

    @Override
    public List<Book> findAllBooksSortByTitle(Pageable pageable) {
        Page<Book> bookPage = bookRepository.findAll(pageable);
        return bookPage.getContent();
    }

}
