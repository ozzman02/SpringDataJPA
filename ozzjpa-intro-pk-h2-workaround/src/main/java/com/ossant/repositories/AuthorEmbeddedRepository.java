package com.ossant.repositories;


import com.ossant.domain.composite.AuthorEmbedded;
import com.ossant.domain.composite.NameId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorEmbeddedRepository extends JpaRepository<AuthorEmbedded, NameId> {
}
