package com.ossant.dao;

import com.ossant.domain.Author;
import com.ossant.repositories.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Component
public class AuthorDaoImpl implements AuthorDao {

    private final AuthorRepository authorRepository;

    public AuthorDaoImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author getById(Long id) {
        return authorRepository.findById(id).orElse(null);
    }

    @Override
    public Author findAuthorByName(String firstName, String lastName) {
        return authorRepository.findByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Author saveNewAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Transactional
    @Override
    public Author updateAuthor(Author author) {
        return authorRepository.findById(author.getId()).map(existingAuthor -> {
            existingAuthor.setFirstName(author.getFirstName());
            existingAuthor.setLastName(author.getLastName());
            return existingAuthor;
        }).orElse(null);
    }

    @Override
    public void deleteAuthorById(Long id) {
        authorRepository.deleteById(id);
    }

    @Override
    public List<Author> findAllByLastNameSortByFirstName(String lastName, Pageable pageable) {
        return List.of();
    }

}
