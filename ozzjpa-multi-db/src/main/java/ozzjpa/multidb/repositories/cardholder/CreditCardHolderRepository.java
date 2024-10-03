package ozzjpa.multidb.repositories.cardholder;

import ozzjpa.multidb.domain.cardholder.CreditCardHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CreditCardHolderRepository extends JpaRepository<CreditCardHolder, Long> {

    Optional<CreditCardHolder> findByCreditCardId(Long id);

}
