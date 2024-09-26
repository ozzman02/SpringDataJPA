package com.ossant.repositories;


import com.ossant.domain.BookNatural;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookNaturalRepository extends JpaRepository<BookNatural, String> {
}
